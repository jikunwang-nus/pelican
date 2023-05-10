package com.charsmart.data.cases.nio.chatroom.handler;

import com.charsmart.data.cases.nio.chatroom.message.ChatMessage;
import com.charsmart.data.cases.nio.chatroom.service.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:29
 */
@ChannelHandler.Sharable
public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) {
        String sendName = msg.getSendName();
        ChannelHandlerContext ct = SessionFactory.getSession().get(sendName);
        ct.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
