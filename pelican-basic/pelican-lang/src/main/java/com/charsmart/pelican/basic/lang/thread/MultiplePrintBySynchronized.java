package com.charsmart.pelican.basic.lang.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/6 15:45
 */
public class MultiplePrintBySynchronized {
    public static void main(String[] args) {
        Object o = new Object();
        CountDownLatch cd = new CountDownLatch(1);
        new Thread(() -> {
            int[] values = new int[]{1, 3, 5, 7, 9};
            for (int value : values) {
                synchronized (o) {
                    System.out.println(value);
                    if (cd.getCount() > 0) cd.countDown();
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "thread1").start();
        new Thread(() -> {
            int[] values = new int[]{2, 4, 6, 8, 10};
            try {
                cd.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int value : values) {
                synchronized (o) {
                    System.out.println(value);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "thread2").start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
