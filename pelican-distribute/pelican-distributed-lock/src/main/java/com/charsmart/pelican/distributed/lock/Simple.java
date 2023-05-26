package com.charsmart.pelican.distributed.lock;

import com.charsmart.pelican.lake.redis.PLock;
import com.charsmart.pelican.lake.redis.PLockRegister;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: Wonder
 * @Date: Created on 2023/5/16 19:50
 */
@Service
public class Simple {
    @Autowired
    private RedissonClient client;

    private final PLockRegister lockRegister;

    public Simple(RedissonClient client, PLockRegister lockRegister) {
        this.client = client;
        this.lockRegister = lockRegister;
    }

    public void testRedission() {
        RLock lock = client.getLock("1");
        lock.lock();
        RTopic topic = client.getTopic("wait_queue_1");
        topic.addListenerAsync(Object.class, new MessageListener<Object>() {
            @Override
            public void onMessage(CharSequence channel, Object msg) {
                System.out.println(msg);
            }
        });
        int count = 10;
        while (count > 0) {
            topic.publish(String.valueOf(count));
            count--;
        }
    }

    public void test() {
        PLock lock = lockRegister.getLock("jk",200);
        try {
            lock.lock();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }

}
