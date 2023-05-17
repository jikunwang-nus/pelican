package com.charsmart.pelican.lake.redis;

import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

/**
 * Pelican lock
 * Using redis command and lua implement distribution lock, provide lock/unlock,reentrant and auto ttl-refresh
 * now support redis single node and redis-cluster which provided by spring-data-redis
 * You can use @See PLockRegister.getLock to get an individual lock instance
 *
 * @Author: Wonder
 * @Date: Created on 2023/5/16 19:20
 */
public class PelicanLock implements PLock {
    private final PLockRegister register;
    private final String lockName;
    private final long uid;

    private final long ttl;

    private final boolean autoRefreshTTL;

    static class Default {
        static long df_ttl = 60L;
        static boolean df_auto_refresh_ttl = true;

        static int max_refresh_count = 5;
    }

    PelicanLock(PLockRegister register, String resourceName) {
        this(register, resourceName, Default.df_ttl);
    }

    PelicanLock(PLockRegister register, String resourceName, long ttl) {
        this(register, resourceName, ttl, Default.df_auto_refresh_ttl);
    }

    PelicanLock(PLockRegister register, String resourceName, long ttl, boolean autoRefreshTTL) {
        this.uid = Thread.currentThread().getId();
        this.register = register;
        this.lockName = resourceName;
        this.ttl = ttl;
        this.autoRefreshTTL = autoRefreshTTL;
    }

    /**
     * blocking lock, hold lock util owner thread release the lock
     */
    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * try to get lock in non-blocked mode,return true or false
     * auto-refresh provided,using local jvm thread(netty timer) to execute refresh task asynchronously
     */
    @Override
    public boolean tryLock() {
        RedisScript<Long> redisScript = register.getLuaScript("try_lock");
        Long val = register.redisTemplate.execute(redisScript,
                Collections.singletonList(lockName),
                String.valueOf(uid),
                String.valueOf(ttl));
        if (val != null && val == 1L) {
            /*
             * auto refresh key ttl to guarantee syn code complete if needed
             * default retry times is 5
             * */
            if (autoRefreshTTL) {
                asyncRefresh(new AtomicInteger(Default.max_refresh_count));
            }
        }
        return val != null && val == 1L;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        RedisScript<Long> redisScript = register.getLuaScript("unlock");
        register.redisTemplate.execute(redisScript, Collections.singletonList(lockName), String.valueOf(uid));
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void asyncRefresh(AtomicInteger retry) {
        this.register.timer.newTimeout(timeout -> {
            try {
                Long result = register.redisTemplate.execute(register.getLuaScript("auto_refresh_ttl"),
                        Collections.singletonList(lockName),
                        String.valueOf(uid), String.valueOf(ttl));
                if (result != null && result == 1) {
                    if (retry.getAndDecrement() > 0) {
                        asyncRefresh(retry);
                    } else {
                        throw new RuntimeException("too long synchronized block");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, calculateDelay(), TimeUnit.SECONDS);
    }

    long calculateDelay() {
        double delayFactor = 0.6;
        return Double.valueOf(ttl * delayFactor).longValue();
    }
}
