package com.charsmart.data.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/30 17:14
 */
@EnableEurekaServer
@SpringBootApplication(scanBasePackages = "com.charsmart.data")
public class EurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class);
    }
}
