package com.charsmart.data.cases.nio.chatroom;

import com.charsmart.data.cases.nio.chatroom.message.ChatMessage;
import com.charsmart.data.cases.nio.chatroom.message.LoginMessageType;
import com.charsmart.data.cases.nio.chatroom.message.LoginResponseMessage;
import com.charsmart.data.cases.nio.chatroom.message.Message;
import com.charsmart.data.cases.nio.chatroom.protocal.MessageCodecShareable;
import com.charsmart.data.cases.nio.chatroom.protocal.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/24 下午5:12
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        LoggingHandler LOGGING = new LoggingHandler();
        MessageCodecShareable MESSAGE_CODEC = new MessageCodecShareable();
        CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        AtomicBoolean LOGIN = new AtomicBoolean(false);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProtocolFrameDecoder());
                    pipeline.addLast(LOGGING);
                    pipeline.addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(new IdleStateHandler(0, 5, 0));
                    ch.pipeline().addLast(new ChannelDuplexHandler() {
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            if (event.state() == IdleState.WRITER_IDLE) {
//                                SessionFactory.getSession().unBind(ctx);
//                                log.info("3s内产生了空闲写事件");
//                                ctx.writeAndFlush(new PingMessage());
                            }
                        }
                    });
                    pipeline.addLast("client ", new SimpleChannelInboundHandler<Message>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
                            if (msg instanceof LoginResponseMessage) {
                                LoginResponseMessage response = (LoginResponseMessage) msg;
                                if (response.isSuccess()) {
                                    LOGIN.set(true);
                                }
                                WAIT_FOR_LOGIN.countDown();
                            } else if (msg instanceof ChatMessage) {
                                ChatMessage message = (ChatMessage) msg;
                                String userName = message.getUserName();
                                String content = message.getMsg();
                                log.info("receive from user {},content {}", userName, content);
                            }
                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            log.info("channel inactive");
                            super.channelInactive(ctx);
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {

                            log.info("channel available!");
                            new Thread(() -> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("username:");
                                String username = scanner.nextLine();
                                System.out.println("password");
                                String password = scanner.nextLine();
                                LoginMessageType message = new LoginMessageType(username, password);
                                ctx.writeAndFlush(message);
                                try {
                                    WAIT_FOR_LOGIN.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!LOGIN.get()) {
                                    System.out.println("用户名或密码错误,客户端退出");
                                    ctx.close();
                                    return;
                                }
                                while (true) {
                                    System.out.println("============");
                                    System.out.println("command list are as follows");
                                    System.out.println("send [username] [message]");
                                    System.out.println("============");
                                    System.out.println("Please select your command...");
                                    Scanner sc = new Scanner(System.in);
                                    String command = sc.nextLine();
                                    String[] ca = command.split(" ");
                                    if ("send".equals(ca[0])) {
                                        String sendName = ca[1];
                                        String msg = ca[2];
                                        ctx.writeAndFlush(new ChatMessage(username, sendName, msg));
                                    } else if ("quit".equals(ca[0])) {
                                        ctx.close();
                                        break;
                                    }
                                }

                            }).start();
                        }
                    });
                }
            });

            Channel channel = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("client err");
        } finally {
            worker.shutdownGracefully();
        }


    }
}
