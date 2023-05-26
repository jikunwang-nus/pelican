package com.charsmart.pelican.lake.redis.pubsub;

import org.springframework.data.redis.listener.Topic;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/19 15:52
 */
public class PTopic implements Topic {
    private String topic;

    PTopic(String channel) {
        this.topic = channel;
    }

    @Override
    public String getTopic() {
        return topic;
    }
}
