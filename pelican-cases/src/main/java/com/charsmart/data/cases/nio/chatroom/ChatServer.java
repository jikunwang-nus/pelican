package com.charsmart.data.cases.nio.chatroom;

import com.charsmart.data.cases.nio.chatroom.protocal.MessageCodecShareable;
import com.charsmart.data.cases.nio.chatroom.protocal.ProtocolFrameDecoder;
import com.charsmart.data.cases.nio.chatroom.handler.ChatMessageHandler;
import com.charsmart.data.cases.nio.chatroom.handler.LoginRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午5:04
 */
@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        LoggingHandler LOGGING = new LoggingHandler();
        MessageCodecShareable MESSAGE_CODEC = new MessageCodecShareable();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatMessageHandler CHAT_HANDLER = new ChatMessageHandler();
        new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtocolFrameDecoder());
                        ch.pipeline().addLast(MESSAGE_CODEC);
                        ch.pipeline().addLast(new IdleStateHandler(30, 0, 0));
                        ch.pipeline().addLast(new ChannelDuplexHandler() {
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                IdleStateEvent event = (IdleStateEvent) evt;
                                if (event.state() == IdleState.READER_IDLE) {
                                    log.info("10s内没有接收到数据");
                                    /*关闭通道*/
//                                    SessionFactory.getSession().unBind(ctx);
//                                    ctx.close();
                                }
                            }
                        });
                        ch.pipeline().addLast(LOGIN_HANDLER);
                        ch.pipeline().addLast(CHAT_HANDLER);
                    }
                }).bind(8080);
    }

}
