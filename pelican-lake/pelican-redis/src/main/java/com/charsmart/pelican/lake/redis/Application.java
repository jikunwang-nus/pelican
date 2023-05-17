package com.charsmart.pelican.lake.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/17 16:29
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.pelican")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
    }
}
