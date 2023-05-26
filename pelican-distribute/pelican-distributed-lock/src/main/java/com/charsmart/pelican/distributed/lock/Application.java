package com.charsmart.pelican.distributed.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/19 11:55
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.pelican")
public class Application {
    @Bean
    public RedissonClient getRedisClient() {
        return Redisson.create();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
        Simple bean = ctx.getBean(Simple.class);
        bean.test();
    }
}
