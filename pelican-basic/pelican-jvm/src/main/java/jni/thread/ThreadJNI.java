package jni.thread;

/**
 * @Author: Wonder
 * @Date: Created on 2022/9/1 3:30 PM
 */
public class ThreadJNI {
    static {
        System.loadLibrary("thread");
    }

    public native void create();
}
