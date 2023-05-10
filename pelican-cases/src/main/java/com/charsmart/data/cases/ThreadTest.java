package com.charsmart.data.cases;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/30 3:20 PM
 */
public class ThreadTest {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
