package com.charsmart.data.basic.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/7 16:51
 */
public class MultiplePrintByLock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition con = lock.newCondition();
        new Thread(() -> {
            int[] values = new int[]{1, 3, 5, 7, 9};
            try {
                lock.lock();
                for (int value : values) {
                    System.out.println(value);
                    con.signal();
                    con.await();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread1").start();
        new Thread(() -> {
            int[] values = new int[]{2, 4, 6, 8, 10};
            try {
                lock.lock();
                for (int value : values) {
                    System.out.println(value);
                    con.signal();
                    con.await();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread2").start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
