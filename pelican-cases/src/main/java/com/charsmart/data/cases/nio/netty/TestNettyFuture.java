package com.charsmart.data.cases.nio.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Author: Wonder
 * @Date: Created on 2022/2/22 下午5:39
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop next = group.next();
        Future<Integer> future = next.submit(() -> {
            Thread.sleep(1000);
            return 60;
        });
        future.addListener(future1 -> log.debug("计算结果 {}", future1.get()));

    }
}
