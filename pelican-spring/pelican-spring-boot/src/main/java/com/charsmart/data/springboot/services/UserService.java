package com.charsmart.data.springboot.services;

import org.springframework.stereotype.Service;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/15 18:26
 */
@Service
public class UserService {
    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
