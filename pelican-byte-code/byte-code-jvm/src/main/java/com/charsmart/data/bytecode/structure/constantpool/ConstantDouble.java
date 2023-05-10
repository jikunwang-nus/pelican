package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 8:01 PM
 */
public class ConstantDouble extends ConstantPoolTopEntry implements Acceptable {
    private int highBytes;
    private int lowBytes;
    private long val;

    public ConstantDouble(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        highBytes = is.readInt();
        lowBytes = is.readInt();
        val = (long) highBytes << 32 + lowBytes;
        return this;
    }
}
