package com.charsmart.data.storage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:26 PM
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.data")
@EnableDubbo(scanBasePackages = "com.charsmart.data.storage")
public class StorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class);
    }
}
