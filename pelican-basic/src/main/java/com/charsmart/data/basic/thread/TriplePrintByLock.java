package com.charsmart.data.basic.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/7 17:02
 */
public class TriplePrintByLock {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition cd1 = lock.newCondition();
        Condition cd2 = lock.newCondition();
        Condition cd3 = lock.newCondition();
        CountDownLatch cdl = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            int[] values = new int[]{1, 4, 7, 10};
            try {
                cdl.await();
                lock.lock();
                for (int i = 0; i < values.length; i++) {
                    int value = values[i];
                    System.out.println(value);
                    cd1.signal();
                    if (i < values.length - 1)
                        cd3.await();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread1");
        thread1.start();
        new Thread(() -> {
            int[] values = new int[]{2, 5, 8};
            try {
                lock.lock();
                if (cdl.getCount() > 0) cdl.countDown();
                for (int value : values) {
                    cd1.await();
                    System.out.println(value);
                    cd2.signal();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread2").start();
        new Thread(() -> {
            int[] values = new int[]{3, 6, 9};
            try {
                lock.lock();
                if (cdl.getCount() > 0) cdl.countDown();
                for (int value : values) {
                    cd2.await();
                    System.out.println(value);
                    cd3.signal();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread3").start();
        thread1.join();
    }
}
