package com.charsmart.data.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/23 10:35 PM
 */
public class GenericHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        byte[] bytes = ByteBufUtil.getBytes((ByteBuf) o);
        String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);
        s = "You picked command " + s;
        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(s.getBytes(StandardCharsets.UTF_8)));
        super.channelRead(ctx, o);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active !!!");
        super.channelActive(ctx);
    }
}
