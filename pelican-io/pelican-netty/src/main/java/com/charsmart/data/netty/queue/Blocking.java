package com.charsmart.data.netty.queue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/14 15:13
 */
public class Blocking {
    public static void main(String[] args) {
        BlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
    }
}
