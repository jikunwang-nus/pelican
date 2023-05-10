package jni.hello;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/31 6:22 PM
 */
public class HelloWorldJNI {
    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        new HelloWorldJNI().sayHello();
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    private native void sayHello();
}
