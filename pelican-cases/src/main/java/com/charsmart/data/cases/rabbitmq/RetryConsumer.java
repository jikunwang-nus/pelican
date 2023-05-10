package com.charsmart.data.cases.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/3/1 下午2:49
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "switch", name = "rabbitmq", havingValue = "true")
public class RetryConsumer {
    @RabbitHandler
    @RabbitListener(queues = {"cs_ping", "bs_ping"})
    public void consume(Channel channel, String msg, Message message) throws IOException {
        log.info("receive message,{}", msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, true);
    }
}
