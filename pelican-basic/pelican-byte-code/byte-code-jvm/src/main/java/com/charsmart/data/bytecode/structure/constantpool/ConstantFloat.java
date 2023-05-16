package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 8:01 PM
 */
public class ConstantFloat extends ConstantPoolTopEntry implements Acceptable {
    private int bytes;
    public ConstantFloat(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        bytes = is.readInt();
        return this;
    }
}
