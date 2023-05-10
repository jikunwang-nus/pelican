package com.charsmart.data.spring.web.loadbalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Wonder
 * @Date: Created on 2023/5/5 18:29
 */
@SpringBootApplication
public class LoadBalanceServer {
    public static void main(String[] args) {
        SpringApplication.run(LoadBalanceServer.class, args);
    }
}
