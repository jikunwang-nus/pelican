package com.charsmart.data.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/20 6:33 PM
 */
public class BioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        try {
            socket.bind(new InetSocketAddress(8083));
            socket.connect(new InetSocketAddress(8082));
            OutputStream ops = socket.getOutputStream();
            ops.write("hello".getBytes(StandardCharsets.UTF_8));
            ops.flush();
            System.out.println("wait...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            socket.close();
        }
    }
}
