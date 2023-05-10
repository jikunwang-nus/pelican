package com.charsmart.data.kafka;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/12 4:07 PM
 */
@RestController
@RequestMapping("/kafka")
public class ProducerController {
    private final KafkaProducer kafkaProducer;

    public ProducerController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PutMapping("/send")
    public void send(String topic, String message) {
        kafkaProducer.send(topic, message);
    }
}
