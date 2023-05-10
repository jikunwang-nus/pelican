package com.charsmart.data.kafka;

import com.charsmart.data.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/12 3:15 PM
 */
@Component
//@ConditionalOnExpression(value = "true == ${mq.consumer}")
@ConditionalOnProperty(name = "mq.consumer", havingValue = "true")
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {TopicConstant.TOPIC})
    public void consume(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        Optional.ofNullable(record.value())
                .ifPresent(r -> {
                    Message message = JsonUtils.toObject((String) r, Message.class);
                    log.info("receive message [message id -->"
                            + message.getId()
                            + ";message content -->"
                            + message.getMessage()
                            + "]");
                });
        ack.acknowledge();
    }
}
