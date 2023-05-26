package com.charsmart.pelican.lake.redis.pubsub;

import com.charsmart.pelican.lake.redis.PelicanLock;
import com.charsmart.pelican.lake.redis.configuration.PelicanPubSubConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/19 15:49
 */
@Component
@ConditionalOnBean(PelicanPubSubConfiguration.class)
public class PelicanPubSub {
    private final RedisMessageListenerContainer listenerContainer;
    private final ConcurrentHashMap<String, MessageListener> channelListener = new ConcurrentHashMap<>();

    public PelicanPubSub(RedisMessageListenerContainer listenerContainer) {
        this.listenerContainer = listenerContainer;
    }

    private ConcurrentHashMap<String, PLockMeta> lockInfoMap = new ConcurrentHashMap<>();

    public static class PLockMeta {
        private PelicanLock originalLock;
        private volatile CountDownLatch cd = new CountDownLatch(1);

        public void block() {
            try {
                cd.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private MessageListener createListener() {
        return new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                String uid = message.toString();
                PLockMeta entry = lockInfoMap.get(uid);
                if (entry != null) {
                    entry.cd.countDown();
                    lockInfoMap.remove(uid);
                }
            }
        };
    }

    public void subscribe(PelicanLock entity, String channel) {
        createEntry(entity);
        MessageListener listener = channelListener.get(channel);
        if (listener == null) {
            listener = createListener();
            channelListener.put(channel, listener);
            listenerContainer.addMessageListener(listener, new ChannelTopic(channel));
        }
    }

    private void createEntry(PelicanLock entity) {
        PLockMeta info = new PLockMeta();
        info.originalLock = entity;
        lockInfoMap.put(String.valueOf(entity.getUid()), info);
    }

    public void block(String uid) {
        lockInfoMap.get(uid).block();
    }
}
