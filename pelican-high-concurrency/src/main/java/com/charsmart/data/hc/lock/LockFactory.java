package com.charsmart.data.hc.lock;

import io.netty.util.HashedWheelTimer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/23 22:03
 */
@Component
public class LockFactory {
    protected final RedisTemplate<String, String> redisTemplate;

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    public LockFactory(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public DistributedLock newLock(String key) {
        return new DistributedLock(this, key, UUID.randomUUID().toString());
    }

    public DistributedLock newLock(String key, long ttl, long timeout) {
        return new DistributedLock(this, key, UUID.randomUUID().toString());
    }

    public DistributedLock newLock(String key, long ttl) {
        return new DistributedLock(this, key, UUID.randomUUID().toString(), ttl);
    }
}
