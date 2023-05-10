package com.charsmart.data.cases.nio.chatroom.service;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午7:01
 */
public class ServiceFactory {
    public static UserService getUserService() {
        return new UserServiceImpl();
    }
}
