package com.charsmart.data.bytecode;

import com.charsmart.data.bytecode.structure.attributes.AttributeInfo;
import com.charsmart.data.bytecode.structure.constantpool.ConstantClass;
import com.charsmart.data.bytecode.structure.constantpool.ConstantDouble;
import com.charsmart.data.bytecode.structure.constantpool.ConstantEntryType;
import com.charsmart.data.bytecode.structure.constantpool.ConstantFieldRef;
import com.charsmart.data.bytecode.structure.constantpool.ConstantFloat;
import com.charsmart.data.bytecode.structure.constantpool.ConstantInteger;
import com.charsmart.data.bytecode.structure.constantpool.ConstantInterfaceMethodRef;
import com.charsmart.data.bytecode.structure.constantpool.ConstantInvokeDynamic;
import com.charsmart.data.bytecode.structure.constantpool.ConstantLong;
import com.charsmart.data.bytecode.structure.constantpool.ConstantMethodHandle;
import com.charsmart.data.bytecode.structure.constantpool.ConstantMethodRef;
import com.charsmart.data.bytecode.structure.constantpool.ConstantMethodType;
import com.charsmart.data.bytecode.structure.constantpool.ConstantNameAndType;
import com.charsmart.data.bytecode.structure.constantpool.ConstantPool;
import com.charsmart.data.bytecode.structure.constantpool.ConstantPoolTopEntry;
import com.charsmart.data.bytecode.structure.constantpool.ConstantString;
import com.charsmart.data.bytecode.structure.constantpool.ConstantUTF8;
import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 6:38 PM
 */
@Data
public class ClassFileInfo {
    private int magic;
    private int minorVersion;
    private int majorVersion;
    private int constantPoolCount;
    private ConstantPool cpInfo;
    private int accessFlag;
    private int thisClass;
    private int superClass;
    private int interfaceCount;
    private int[] interfaces;
    private int fieldsCount;
    private FieldInfo[] fields;
    private int methodCount;
    private MethodInfo[] methods;
    private int attributesCount;
    private AttributeInfo[] attributes;

    public ClassFileInfo init(DataInputStream is) throws IOException {
        int magic = is.readInt();
        if (magic != 0xCAFEBABE) throw new RuntimeException("not a valid class file !");
        /*2 minor version*/
        minorVersion = is.readUnsignedShort();
        /*3 major version*/
        majorVersion = is.readUnsignedShort();
        /*4 constant pool count*/
        /*5 init constant pool*/
        initConstantPool(is);
        /*6 access flag*/
        accessFlag = is.readUnsignedShort();
        /*7 this class*/
        thisClass = is.readUnsignedShort();
        /*8 super class*/
        superClass = is.readUnsignedShort();
        initInterfaces(is);
        /*11 fields*/
        initFields(is);
        /*12 methods*/
        initMethods(is);
        /*13 attributes*/
        initAttributes(is);
        return this;
    }

    private void initConstantPool(DataInputStream is) throws IOException {
        int count = is.readUnsignedShort();
        cpInfo = new ConstantPool();
        List<ConstantPoolTopEntry> entries = new ArrayList<>();
        entries.add(null);
        while (count > 1) {
            int b = is.readByte();
            try {
                ConstantPoolTopEntry entry;
                switch (b) {
                    case ConstantEntryType.CONSTANT_Utf8:
                        entry = new ConstantUTF8(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Integer:
                        entry = new ConstantInteger(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Float:
                        entry = new ConstantFloat(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Long:
                        entry = new ConstantLong(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Double:
                        entry = new ConstantDouble(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Class:
                        entry = new ConstantClass(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_String:
                        entry = new ConstantString(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Fieldref:
                        entry = new ConstantFieldRef(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_Methodref:
                        entry = new ConstantMethodRef(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_InterfaceMethodref:
                        entry = new ConstantInterfaceMethodRef(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_NameAndType:
                        entry = new ConstantNameAndType(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_MethodHandle:
                        entry = new ConstantMethodHandle(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_MethodType:
                        entry = new ConstantMethodType(b).accept(is);
                        break;
                    case ConstantEntryType.CONSTANT_InvokeDynamic:
                        entry = new ConstantInvokeDynamic(b).accept(is);
                        break;
                    default:
                        throw new RuntimeException("err type");
                }
                entries.add(entry);
            } finally {
                count--;
            }

        }
        cpInfo.setEntries(entries);
    }

    public void initFields(DataInputStream is) throws IOException {
        int count = is.readUnsignedShort();
        fields = new FieldInfo[count];
        while (count > 0) {
            fields[fields.length - count] = new FieldInfo().load(cpInfo, is);
            count--;
        }
    }

    public void initInterfaces(DataInputStream is) throws IOException {
        int count = is.readUnsignedShort();
        int length = count;
        interfaces = new int[length];
        while (count > 0) {
            interfaces[length - count] = is.readUnsignedShort();
            count--;
        }
    }

    public void initMethods(DataInputStream is) throws IOException {
        int count = is.readUnsignedShort();
        methods = new MethodInfo[count];
        while (count > 0) {
            methods[methods.length - count] = new MethodInfo().load(cpInfo, is);
            count--;
        }
    }

    public void initAttributes(DataInputStream is) throws IOException {
        attributes = AttributeInfo.createAttrArray(cpInfo, is);
    }
}
