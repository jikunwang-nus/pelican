package com.charsmart.data.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/19 5:36 PM
 */
public class BioServer {
    public static void main(String[] args) {
        try {
            try (ServerSocket ss = new ServerSocket()) {
                ss.bind(new InetSocketAddress(8082));
                while (true) {
                    System.out.println("waiting for connect ...");
                    Socket socket = ss.accept();
                    System.out.println("receive new connection from ip "
                            + socket.getInetAddress().getHostAddress()
                            + " port " + socket.getPort());
                    new Thread(() -> {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            StringBuilder sb = new StringBuilder();
                            int n;
                            while ((n = inputStream.read()) > 0) {
                                sb.append((char) n);
                                if (inputStream.available() == 0) {
                                    String message = sb.toString().replace("\r\n", "");
                                    System.out.println("[" + Thread.currentThread().getName() + "-"
                                            + socket.getPort() + "]"
                                            + message);
                                    if ("bye".equals(message)) {
                                        socket.close();
                                    }
                                    sb = new StringBuilder();
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }).start();

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
