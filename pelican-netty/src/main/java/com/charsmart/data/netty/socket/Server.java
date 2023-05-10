package com.charsmart.data.netty.socket;


import com.charsmart.data.netty.utils.StackUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/9 14:59
 */
public class Server {
    public static void main(String[] args) throws IOException {
        /*BIO*/
//        try (ServerSocket socket = new ServerSocket()) {
//            socket.bind(new InetSocketAddress(8001));
//            while (true) {
//                Socket so = socket.accept();
//                InputStream is = so.getInputStream();
//                byte[] buf = new byte[32];
//                int index = 0;
//                byte v;
//                while ((v = (byte) is.read()) != -1) {
//                    buf[index++] = v;
//                }
//                System.out.println(new String(buf));
//            }
//        }
        /*NIO*/
//        ServerSocketChannel server = ServerSocketChannel.open();
//        server.bind(new InetSocketAddress(8001));
//        server.configureBlocking(false);
//        try (Selector selector = Selector.open()) {
//            server.register(selector, SelectionKey.OP_ACCEPT);
//            while (true) {
//                selector.select();
//                Set<SelectionKey> keys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = keys.iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey key = iterator.next();
//                    iterator.remove();
//                    if (key.isAcceptable()) {
//                        SocketChannel sc = server.accept();
//                        sc.configureBlocking(false);
//                        sc.register(selector, SelectionKey.OP_READ);
//                    } else if (key.isReadable()) {
//                        SocketChannel channel = (SocketChannel) key.channel();
//                        ByteBuffer buf = ByteBuffer.allocate(1024);
//                        channel.read(buf);
//                        String response = "Receive from [" + channel.getRemoteAddress().toString() + "],content = ";
//                        System.out.println(response + new String(buf.array()));
//                        channel.write(ByteBuffer.wrap("ack receive !".getBytes(StandardCharsets.UTF_8)));
//                    }
//                }
//            }
//        }
        /*Netty*/
        NioEventLoopGroup core = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap().group(core, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("handler", new SimpleChannelInboundHandler<Object>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                int capacity = buf.readableBytes();
                                byte[] bytes = new byte[capacity];
                                buf.readBytes(bytes);
                                System.out.println(new String(bytes));
                                ctx.writeAndFlush(Unpooled.copiedBuffer("ack".getBytes(StandardCharsets.UTF_8)));
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                Channel channel = ctx.channel();
                                System.out.println("Channel active ![id " + channel.id() + " , address " + channel.remoteAddress() + "]");
//                                super.channelActive(ctx);
//                                System.out.println(StackUtils.print());
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                Channel channel = ctx.channel();
                                System.out.println("Channel inactive ![id " + channel.id() + " , address " + channel.remoteAddress() + "]");
                            }
                        });
                    }
                });
        server.validate();
        try {
            ChannelFuture future = server.bind(8001).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            core.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
