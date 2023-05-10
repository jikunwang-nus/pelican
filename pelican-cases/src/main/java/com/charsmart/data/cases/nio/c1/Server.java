package com.charsmart.data.cases.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/21 下午5:47
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        log.info("Server Start...");
        log.info("Service bind to port...");
        List<SocketChannel> channels = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        while (true) {
            selector.select();
            log.info("waiting for connect...");
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            log.info("connected...");
            channels.add(sc);
            for (SocketChannel channel : channels) {
                channel.read(buffer);
                buffer.flip();
                log.info(Arrays.toString(buffer.array()));
                buffer.clear();
            }
        }
    }
}
