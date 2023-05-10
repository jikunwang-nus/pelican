package com.charsmart.data.cases.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/22 下午3:48
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        DefaultEventLoopGroup defaultGroup = new DefaultEventLoopGroup();
        //1 启动器
        new ServerBootstrap()
                /*2 事件循环组*/
                /* 可以指定boss和worker分别做建立连接和读写操作*/
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                /* 3 指定channel的实现类*/
                .channel(NioServerSocketChannel.class)
                /* 4 指定处理器*/
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    /*5 建立连接后调用的初始化方法*/
                    protected void initChannel(NioSocketChannel ch) {
                        /* 6 自定义处理器*/
                        ch.pipeline()
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        String s = msg.toString(StandardCharsets.UTF_8);
                                        log.info("Receive message from client:  {}", s);
                                        log.info("Respond to client {}", s);
                                        ctx.writeAndFlush(msg);
//                                        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server response".getBytes()));
                                    }
                                });

                    }
                }).bind(8080);
    }
}
