package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:51 PM
 */
public class ConstantUTF8 extends ConstantPoolTopEntry implements Acceptable {
    private int length;
    private byte[] bytes;
    private String val;

    public ConstantUTF8(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        int length = is.readUnsignedShort();
        bytes = new byte[length];
        is.read(bytes);
        val = new String(bytes, StandardCharsets.UTF_8);
        return this;
    }

    public String getVal() {
        return val;
    }
}
