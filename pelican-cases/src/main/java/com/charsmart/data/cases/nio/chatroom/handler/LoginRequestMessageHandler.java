package com.charsmart.data.cases.nio.chatroom.handler;

import com.charsmart.data.cases.nio.chatroom.message.LoginMessageType;
import com.charsmart.data.cases.nio.chatroom.message.LoginResponseMessage;
import com.charsmart.data.cases.nio.chatroom.service.ServiceFactory;
import com.charsmart.data.cases.nio.chatroom.service.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/25 上午11:25
 */
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginMessageType> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("new client accept: {}", ctx);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginMessageType msg) throws Exception {
        String name = msg.getName();
        String password = msg.getPassword();
        boolean login = ServiceFactory.getUserService().login(name, password);
        LoginResponseMessage loginResponseMessage;
        if (login) {
            /*登陆成功之后记录username和channel的关系*/
            SessionFactory.getSession().bind(name, ctx);
            loginResponseMessage = new LoginResponseMessage(true, "login successfully!");
        } else {
            loginResponseMessage = new LoginResponseMessage(false, "login failed!");
        }
        ctx.writeAndFlush(loginResponseMessage);
//        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unBind(ctx);
        super.channelInactive(ctx);
    }
}
