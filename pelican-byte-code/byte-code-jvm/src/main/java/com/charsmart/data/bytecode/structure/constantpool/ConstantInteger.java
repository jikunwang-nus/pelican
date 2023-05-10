package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:56 PM
 */
public class ConstantInteger extends ConstantPoolTopEntry implements Acceptable {
    private int bytes;
    public ConstantInteger(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        bytes = is.readInt();
        return this;
    }
}
