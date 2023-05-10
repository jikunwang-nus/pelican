package com.charsmart.data.kafka;

import com.charsmart.data.common.global.SnowId;
import com.charsmart.data.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/12 3:16 PM
 */
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SnowId snowId;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, SnowId snowId) {
        this.kafkaTemplate = kafkaTemplate;
        this.snowId = snowId;
    }

    public void send(String topic, String msg) {
        Message message = new Message().setId(snowId.nextId())
                .setMessage(msg)
                .setCreateTime(LocalDateTime.now());
        String content = JsonUtils.toJson(message);
        kafkaTemplate.send(topic, content);
        log.info("send message to topic [" + topic + "],content -->" + content);
    }
}
