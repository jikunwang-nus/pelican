package com.charsmart.data.cases.nio.chatroom.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:31
 */
public class SessionServiceImpl implements SessionService {
    private final ConcurrentHashMap<String, ChannelHandlerContext> sessionMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<ChannelHandlerContext, String> ctxUserMap = new ConcurrentHashMap<>();

    @Override
    public void bind(String username, ChannelHandlerContext ctx) {
        sessionMap.put(username, ctx);
        ctxUserMap.put(ctx, username);
    }

    @Override
    public void unBind(ChannelHandlerContext ctx) {
        String userName = ctxUserMap.get(ctx);
        if (userName != null) {
            ctxUserMap.remove(ctx);
            sessionMap.remove(userName);
        }
    }

    @Override
    public ChannelHandlerContext get(String username) {
        return sessionMap.get(username);
    }
}
