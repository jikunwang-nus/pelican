package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:48 PM
 */
public class ConstantNameAndType extends ConstantPoolTopEntry implements Acceptable {
    private int nameIndex;
    private int descriptorIndex;

    public ConstantNameAndType(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        nameIndex = is.readUnsignedShort();
        descriptorIndex = is.readUnsignedShort();
        return this;
    }
}
