package com.charsmart.data.bytecode.structure.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 10:47 PM
 */
public class ConstantInterfaceMethodRef extends ConstantPoolTopEntry implements Acceptable {
    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantInterfaceMethodRef(int tag) {
        super(tag);
    }

    @Override
    public ConstantPoolTopEntry accept(DataInputStream is) throws IOException {
        classIndex = is.readUnsignedShort();
        nameAndTypeIndex = is.readUnsignedShort();
        return this;
    }
}
