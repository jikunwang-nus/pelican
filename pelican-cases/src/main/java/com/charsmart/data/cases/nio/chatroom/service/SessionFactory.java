package com.charsmart.data.cases.nio.chatroom.service;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:37
 */
public class SessionFactory {
    private static SessionService sessionService;

    public static SessionService getSession() {
        if (sessionService == null) {
            sessionService = new SessionServiceImpl();
        }
        return sessionService;
    }
}
