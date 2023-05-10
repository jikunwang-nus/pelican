package com.charsmart.data.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Wonder
 * @Date: Created on 2022/6/17 4:22 PM
 */
@SpringBootApplication
public class NacosClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosClientApplication.class);
        String old = "false";
        while (true) {
            String cacheable = applicationContext.getEnvironment().getProperty("cacheable");
            cacheable = cacheable == null ? "" : cacheable;
            if (!old.equals(cacheable)) {
                System.out.println(LocalDateTime.now() + cacheable);
                old = cacheable;
            }
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
