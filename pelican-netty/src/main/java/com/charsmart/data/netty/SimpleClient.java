package com.charsmart.data.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/18 5:38 PM
 */
public class SimpleClient {
    public static void main(String[] args) {
        Bootstrap client = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            client.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("client channel active");
                                    ByteBuf byteBuf = ctx.alloc().buffer(256).writeBytes("hello".getBytes(StandardCharsets.UTF_8));
                                    ctx.writeAndFlush(byteBuf);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    ctx.flush();
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    String s = new String(ByteBufUtil.getBytes((ByteBuf) msg), StandardCharsets.UTF_8);
                                    System.out.println(s);
                                }
                            });
                        }
                    });
            ChannelFuture future = client.connect(new InetSocketAddress(8009));
            Channel channel = future.sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture channelFuture = null;
            for (; ; ) {
                String s = reader.readLine();
                if (s == null) continue;
                channelFuture = channel.writeAndFlush(channel.alloc().buffer().writeBytes((s + "\r\n").getBytes(StandardCharsets.UTF_8)));
                if (s.startsWith("bye")) {
                    channel.closeFuture().sync();
                    break;
                }
            }
            if (channelFuture != null) {
                channelFuture.sync();
            }
//            channel.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
