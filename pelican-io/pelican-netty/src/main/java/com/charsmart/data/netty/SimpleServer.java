package com.charsmart.data.netty;

import com.charsmart.data.netty.handler.GenericHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/18 5:25 PM
 */
public class SimpleServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap server = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline cp = socketChannel.pipeline();
//                            GrCodec grCodec = new GrCodec();
//                            cp.addLast("generic-decode", grCodec.getDecoder())
//                                    .addLast("generic-encode", grCodec.getEncoder());
                            cp.addLast("generic", new GenericHandler());
                        }
                    });
            ChannelFuture future = server.bind(8009).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
