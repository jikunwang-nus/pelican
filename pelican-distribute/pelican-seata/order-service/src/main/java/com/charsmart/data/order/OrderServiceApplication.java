package com.charsmart.data.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/6 5:32 PM
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.data")
@EnableDubbo(scanBasePackages = "com.charsmart.data.order")
@MapperScan(basePackages = "com.charsmart.data.order.repository")
public class OrderServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderServiceApplication.class);
        System.out.println(context);
    }
}
