package com.charsmart.data.hc.lock;

import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/27 17:29
 */
public class DistributedLock {
    private final LockFactory lockFactory;
    //    private final ScheduledThreadPool executor = new ScheduledThreadPool(2);
    String key;
    String uid;
    private final long ttl;
    private final long timeout;
    private final long delay;
    private final boolean autoRefreshAlive;
    private static final long DF_TTL = 30L;
    private static final long DF_TIME_OUT = 5L;
    private static final long DF_DELAY = 5L;
    private static final String LUA_LOCK_RELEASE;
    private static final String LUA_LOCK_ALIVE_REFRESH;

    private static final Map<String, RedisScript<Long>> scripts = new HashMap<>();

    static {
        LUA_LOCK_RELEASE = "local value = redis.call('get',KEYS[1])" +
                "if type(value)==\"nil\" then " +
                " return 1;" +
                "end;" +
                "if value == ARGV[1] then" +
                "    return redis.call('del',KEYS[1])" +
                "end;" +
                "return 0;";
        DefaultRedisScript<Long> luaLockRelease = new DefaultRedisScript<>(LUA_LOCK_RELEASE, Long.TYPE);
        scripts.put(LUA_LOCK_RELEASE, luaLockRelease);

        LUA_LOCK_ALIVE_REFRESH = "local value = redis.call('get',KEYS[1])" +
                "if type(value)==\"nil\" then " +
                "    return 0;" +
                "end;" +
                "if value == ARGV[1] then " +
                "    return redis.call('expire',KEYS[1],ARGV[2]) " +
                "end; " +
                "return 0;";
        DefaultRedisScript<Long> luaLockRefreshScript = new DefaultRedisScript<>(LUA_LOCK_ALIVE_REFRESH, Long.TYPE);
        scripts.put(LUA_LOCK_ALIVE_REFRESH, luaLockRefreshScript);
    }

    DistributedLock(LockFactory lockFactory, String key, String uid) {
        this(lockFactory, key, uid, DF_TTL, DF_TIME_OUT, true, DF_DELAY);
    }

    DistributedLock(LockFactory lockFactory, String key, String uid, long ttl) {
        this(lockFactory, key, uid, ttl, DF_TIME_OUT, true, DF_DELAY);
    }

    DistributedLock(LockFactory lockFactory, String key, String uid,
                    long ttl, long timeout, boolean autoRefreshAlive,
                    long delay) {
        this.lockFactory = lockFactory;
        this.key = key;
        this.uid = uid;
        this.ttl = ttl;
        this.timeout = timeout;
        this.autoRefreshAlive = autoRefreshAlive;
        this.delay = delay;
    }

    public void lock() {
        lock(ttl, timeout);
    }

    @SuppressWarnings("unused")
    public boolean tryLock() {
        return lock(ttl, 0L);
    }

    public boolean lock(long ttl, long timeout) {

        long start = System.currentTimeMillis();
        //在一定时间内获取锁，超时则返回错误
        for (; ; ) {
            //Set命令返回OK，则证明获取锁成功
            // set key value nx
            boolean ret = Boolean.TRUE.equals(lockFactory.redisTemplate.opsForValue().setIfAbsent(key, uid, ttl, TimeUnit.SECONDS));
            if (ret) {
                /*若同步块的执行时间过长, 需要更新alive保证同步块执行完毕*/
                if (autoRefreshAlive) {
                    offerTask(new AtomicInteger(5));
                }
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            long end = System.currentTimeMillis() - start;
            if (end >= timeout) {
                return false;
            }
        }
    }


    private void offerTask(AtomicInteger retry) {
        this.lockFactory.timer.newTimeout(timeout -> {
            try {
                Long result = lockFactory.redisTemplate.execute(scripts.get(LUA_LOCK_ALIVE_REFRESH),
                        Collections.singletonList(key),
                        uid, String.valueOf(ttl));
                if (result != null && result == 1) {
                    if (retry.getAndDecrement() > 0) {
                        offerTask(retry);
                    } else {
                        throw new RuntimeException("too long synchronized block");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, delay, TimeUnit.SECONDS);
    }

    public void unlock() {
        this.lockFactory.redisTemplate.execute(scripts.get(LUA_LOCK_RELEASE), Collections.singletonList(key), uid);
    }
}
