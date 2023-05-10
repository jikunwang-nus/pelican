package com.charsmart.data.springboot;

import com.charsmart.data.springboot.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/15 18:24
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
        UserService userService = ctx.getBean(UserService.class);
        userService.setUsername("wonder");
        System.out.println(userService.getUsername());
    }
}
