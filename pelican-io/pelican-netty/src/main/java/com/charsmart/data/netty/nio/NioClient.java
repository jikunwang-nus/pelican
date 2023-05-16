package com.charsmart.data.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/23 2:25 PM
 */
public class NioClient {
    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.configureBlocking(false);
//            sc.bind(new InetSocketAddress(8079));
            sc.connect(new InetSocketAddress(8080));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
