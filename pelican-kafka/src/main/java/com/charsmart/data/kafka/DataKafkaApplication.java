package com.charsmart.data.kafka;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/12 4:18 PM
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.data")
public class DataKafkaApplication {
    public static void main(String[] args) {
        val run = SpringApplication.run(DataKafkaApplication.class, args);
        KafkaConsumer bean = run.getBean(KafkaConsumer.class);
        System.out.println(bean);
    }
}
