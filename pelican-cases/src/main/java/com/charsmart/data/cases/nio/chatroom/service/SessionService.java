package com.charsmart.data.cases.nio.chatroom.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:31
 */
public interface SessionService {
    void bind(String username, ChannelHandlerContext ctx);

    void unBind(ChannelHandlerContext ctx);

    ChannelHandlerContext get(String username);
}
