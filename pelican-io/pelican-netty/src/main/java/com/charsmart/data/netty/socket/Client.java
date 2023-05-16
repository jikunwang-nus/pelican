package com.charsmart.data.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/9 15:08
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Selector selector;
        AtomicBoolean on = new AtomicBoolean(true);
        try (SocketChannel sc = SocketChannel.open()) {
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress(8001));
            while (!sc.finishConnect()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
            selector = Selector.open();
            sc.register(selector, SelectionKey.OP_READ);
            new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while (true) {
                    try {
                        if ((line = reader.readLine()) != null) {
                            if ("quit".equals(line)) {
                                on.set(false);
                                break;
                            }
                            for (int i = 0; i < 200; i++) {
                                ByteBuffer buf = ByteBuffer.wrap(line.getBytes(StandardCharsets.UTF_8));
                                sc.write(buf);
                                String send = "Send to [" + sc.getRemoteAddress().toString() + "],content = " + line;
                                System.out.println(send);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "write").start();
            while (on.get()) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    keys.remove(key);
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(128);
                        channel.read(buf);
                        System.out.println(new String(buf.array()));
                    }
                }
            }
        }
    }
}
