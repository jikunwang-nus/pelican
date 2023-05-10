package com.charsmart.data.spring.cloud.service.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/30 21:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class InventoryServiceApplication {
    @PutMapping("/inv/reduce")
    public Integer reduce(String id) {
        return 1;
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
