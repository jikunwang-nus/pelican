package com.charsmart.data.bytecode.structure.constantpool;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 7:10 PM
 */
public interface ConstantEntryType {
    int CONSTANT_NULL0 = 0;
    int CONSTANT_Utf8 = 1;
    int CONSTANT_NULL2 = 2;
    int CONSTANT_Integer = 3;
    int CONSTANT_Float = 4;
    int CONSTANT_Long = 5;
    int CONSTANT_Double = 6;
    int CONSTANT_Class = 7;
    int CONSTANT_String = 8;
    int CONSTANT_Fieldref = 9;
    int CONSTANT_Methodref = 10;
    int CONSTANT_InterfaceMethodref = 11;
    int CONSTANT_NameAndType = 12;
    int CONSTANT_NULL13 = 13;
    int CONSTANT_NULL14 = 14;
    int CONSTANT_MethodHandle = 15;
    int CONSTANT_MethodType = 16;
    int CONSTANT_NULL17 = 17;
    int CONSTANT_InvokeDynamic = 18;
}
