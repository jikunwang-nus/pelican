package com.charsmart.data.cases.nio.chatroom.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午7:00
 */
public class UserServiceImpl implements UserService {
    private Map<String, String> userSecretMap = new HashMap<>();

    UserServiceImpl() {
        userSecretMap.put("wonder", "123456");
        userSecretMap.put("msl", "123456");
    }

    @Override
    public boolean login(String username, String password) {
        return userSecretMap.containsKey(username) && password.equals(userSecretMap.get(username));
    }
}
