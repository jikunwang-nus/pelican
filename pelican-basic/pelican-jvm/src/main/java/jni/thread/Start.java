package jni.thread;

/**
 * @Author: Wonder
 * @Date: Created on 2022/9/1 4:33 PM
 */
public class Start {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new ThreadJNI().create();
    }
}
