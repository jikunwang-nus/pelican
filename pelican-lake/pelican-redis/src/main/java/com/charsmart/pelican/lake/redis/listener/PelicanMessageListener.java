package com.charsmart.pelican.lake.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/23 18:46
 */
public class PelicanMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
