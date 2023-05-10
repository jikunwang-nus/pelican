package com.charsmart.data.cases.nio.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/22 下午5:42
 */
public class TestNettyPromise {
    public static void main(String[] args) {
        EventLoop eventLoop = new NioEventLoopGroup().next();
    }
}
