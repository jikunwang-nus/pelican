package com.charsmart.pelican.distributed.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/16 19:50
 */
public class Simple {
    private RedissonClient client;
    public void test(){
        RLock key = client.getLock("key");
        key.lock();
    }

}
