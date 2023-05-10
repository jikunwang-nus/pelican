package com.charsmart.data.netty.queue;

import com.charsmart.data.netty.utils.UnsafeUtil;
import io.netty.channel.Channel;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @Author: Wonder
 * @Date: Created on 2023/3/14 17:29
 */
public class Memory {

    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtil.unsafe();
        long address = unsafe.allocateMemory(8);
        int value = 0x01020304;
        int value2 = 0x04030201;
        unsafe.putInt(address, value);
        unsafe.putInt(address + 4, value2);
        for (int i = 0; i < 8; i++) {
            System.out.println("[" + address + i + "]" + unsafe.getByte(address + i));
        }
    }
}
