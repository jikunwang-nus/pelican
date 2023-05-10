package com.charsmart.data.netty.nio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledDirectByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/20 12:40 PM
 */
public class NioServer {
    public static void main(String[] args) {
        try (ServerSocketChannel ss = ServerSocketChannel.open()) {
            ss.bind(new InetSocketAddress(8080));
            ss.configureBlocking(false);
            Selector selector = Selector.open();
            ss.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {
                        SocketChannel sc = ss.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);

                    } else if (next.isReadable()) {
                        SocketChannel sc = (SocketChannel) next.channel();
                        ByteBuffer buf = ByteBuffer.allocate(128);
                        int read = sc.read(buf);
                        if (read > 0) {
                            System.out.println("first char " + new String(buf.array()));
                        }
                        byte[] bytes = "ack".getBytes(StandardCharsets.UTF_8);
                        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
                        buffer.put(bytes);
                        buffer.flip();
                        sc.write(buffer);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
