package com.charsmart.data.bytecode;

import com.charsmart.data.bytecode.structure.attributes.AttributeInfo;
import com.charsmart.data.bytecode.structure.constantpool.ConstantPool;
import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 6:48 PM
 */
@Data
public class MethodInfo {
    private int accessFlags;
    private int nameIndex;
    private int descriptorIndex;
    private int attributesCount;
    private AttributeInfo[] attributes;

    public MethodInfo load(ConstantPool cp, DataInputStream is) throws IOException {
        accessFlags = is.readUnsignedShort();
        nameIndex = is.readUnsignedShort();
        descriptorIndex = is.readUnsignedShort();
        attributes = AttributeInfo.createAttrArray(cp, is);
        return this;
    }
}
