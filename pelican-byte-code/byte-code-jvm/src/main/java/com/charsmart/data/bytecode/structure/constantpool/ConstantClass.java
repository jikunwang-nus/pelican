package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:27 PM
 */
public class ConstantClass extends ConstantPoolTopEntry implements Acceptable {
    private int nameIndex;

    public ConstantClass(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        nameIndex = is.readUnsignedShort();
        return this;
    }
}
