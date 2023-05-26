package com.charsmart.pelican.lake.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
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
    private final Logger logger = LoggerFactory.getLogger(PelicanLock.class);
    private final PLockRegister register;
    private final String lockName;
    private final long uid;

    private final long ttl;

    private final boolean autoRefreshTTL;

    public String getLockName() {
        return lockName;
    }

    public long getUid() {
        return uid;
    }

    static class Default {
        static long df_ttl = 1000 * 60L;
        static boolean df_auto_refresh_ttl = true;

        static int max_refresh_count = 5;
    }

    static class LuaScript {
        static String TRY_LOCK = "try_lock";
        static String LOCK = "lock";
        static String UNLOCK = "unlock";
        static String AUTO_REFRESH_TTL = "auto_refresh_ttl";
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
        this.ttl = ttl * 1000L;
        this.autoRefreshTTL = autoRefreshTTL;
    }

    /**
     * blocking lock, hold lock util owner thread release the lock
     */
    @Override
    public void lock() {
        boolean success = tryAcquire();
        if (success) {
            logger.info("[lock][" + uid + "]lock success !" + this);
            return;
        }
        /*
        if fail to acquire,
        1.add current thread to wait queue
        2.subscribe channel
        3.block myself

        blocking current thread by spinning and subscribe channel
        wait for message to get chances to acquire again

        * */
        addCurrentToQueue();
        register.pubSub.subscribe(this, getChannelName(lockName));
        while (true) {
            /*try again*/
            boolean acquire = tryAcquire();
            if (acquire) {
                //acquire lock , break block
                logger.info("[lock][" + uid + "]lock success !" + this);
                break;
            }
            /*if fail to acquire , block self */
            register.pubSub.block(String.valueOf(uid));
        }
    }

    private void addCurrentToQueue() {
        register.redisTemplate.opsForList().rightPush(getWaitQueueName(lockName), String.valueOf(uid));
    }

    String getChannelName(String name) {
        return "[awake_channel]-" + name;
    }

    String getWaitQueueName(String name) {
        return "[wait_queue]-" + name;
    }

    private void asyncSubscribe() {
        String channel = "ch_" + lockName;
        String queue = "queue_" + lockName;
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
        return tryLock(-1L, TimeUnit.SECONDS);
    }

    private boolean tryAcquire() {
        RedisScript<Long> redisScript = register.getLuaScript(LuaScript.TRY_LOCK);
        Long val = register.redisTemplate.execute(redisScript,
                Collections.singletonList(lockName),
                String.valueOf(uid),
                String.valueOf(ttl));
        if (val != null && val == 1L) {
            /*
             * auto refresh key ttl to guarantee syn code complete if needed
             * default retry times is 5
             * */
//            if (autoRefreshTTL) {
//                asyncRefresh(new AtomicInteger(Default.max_refresh_count));
//            }
        }
        return val != null && val == 1L;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) {
        /*it is unnecessary to wait when timeout smaller than zero, just return
         * */
        if (timeout < 0) {
            return tryAcquire();
        }
        boolean acquire = tryAcquire();
        if (acquire) return true;
        /* when fail to acquire lock,try to acquire util the timeout
         * */
        CountDownLatch cd = new CountDownLatch(1);
        register.timer.newTimeout(t -> cd.countDown(), timeout, unit);
        while (cd.getCount() > 0) {
            boolean ac = tryAcquire();
            if (ac) return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        RedisScript<Long> redisScript = register.getLuaScript(LuaScript.UNLOCK);
        register.redisTemplate.execute(redisScript,
                Arrays.asList(lockName,
                        getWaitQueueName(lockName),
                        getChannelName(lockName)),
                String.valueOf(uid));
        logger.info("[unlock][" + uid + "]lock release success !" + this);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private void asyncRefresh(AtomicInteger retry) {
        this.register.timer.newTimeout(t -> {
            try {
                Long result = register.redisTemplate.execute(register.getLuaScript(LuaScript.AUTO_REFRESH_TTL),
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
