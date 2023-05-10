package com.charsmart.data.basic.thread;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/7 17:34
 */
public class TriplePrintBySynchronized {
    public static void main(String[] args) {
        Object o = new Object();
        Thread thread1 = new Thread(() -> {
            int[] values = new int[]{1, 4, 7, 10};
            synchronized (o) {
                for (int i = 0; i < values.length; i++) {
                    int value = values[i];
                    System.out.println(value);
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "thread1");
        Thread thread2 = new Thread(() -> {
            int[] values = new int[]{2, 5, 8};
            synchronized (o) {
                for (int i = 0; i < values.length; i++) {
                    try {
                        o.wait();
                        int value = values[i];
                        System.out.println(value);
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "thread2");
    }
}
