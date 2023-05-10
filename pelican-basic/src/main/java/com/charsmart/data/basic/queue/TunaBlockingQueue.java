package com.charsmart.data.basic.queue;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/7 17:53
 */
public class TunaBlockingQueue<T> {
    TunaBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    Node head;
    Node tail;
    private final AtomicInteger size = new AtomicInteger(0);
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullCond = lock.newCondition();
    private final Condition emptyCond = lock.newCondition();

    class Node {
        T value;
        Node next;

        Node(T value) {
            this.value = value;
        }
    }

    boolean isEmpty() {
        return size.get() == 0;
    }

    boolean isFull() {
        return size.get() >= capacity;
    }

    public void offer(T value) {
        try {
            lock.lock();
            while (isFull()) {
                fullCond.await();
            }
            enq(new Node(value));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private void enq(Node node) {
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size.incrementAndGet();
        emptyCond.signal();
    }

    public T take() {
        try {
            lock.lock();
            while (isEmpty()) {
                emptyCond.await();
            }
            Node node = head;
            assert node != null;
            return node.value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public T poll() {
        try {
            lock.lock();
            while (isEmpty()) {
                emptyCond.await();
            }
            Node node = dequeue();
            assert node != null;
            return node.value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private Node dequeue() {
        Node first = head;
        head = head.next;
        if (head == null) tail = null;
        size.decrementAndGet();
        fullCond.signal();
        return first;
    }

    public static void main(String[] args) throws InterruptedException {
        TunaBlockingQueue<Runnable> queue = new TunaBlockingQueue<>(2);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                int finalI = i;
                Runnable runnable = () -> System.out.println("offer" + finalI);
                queue.offer(runnable);
            }
        }, "offer");
        thread.start();
        new Thread(() -> {
            while (true) {
                Runnable run = queue.poll();
                run.run();
                System.out.println(LocalDateTime.now());
            }
        }, "consume").start();
        thread.join();
    }
}
