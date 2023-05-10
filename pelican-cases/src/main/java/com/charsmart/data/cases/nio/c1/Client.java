package com.charsmart.data.cases.nio.c1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/21 下午5:51
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("waiting...");
        ByteBuffer wrap = ByteBuffer.wrap("hello baby".getBytes());
        sc.write(wrap);
    }
}
