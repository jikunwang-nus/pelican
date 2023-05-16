package com.charsmart.data.bytecode;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 6:27 PM
 */
public class ByteCode {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        String classPath = System.getProperty("user.dir") + "/pelican-byte-code/target/classes/com/charsmart/data/bytecode/";
        String className = classPath + "User.class";
        ClassFileInfo classFileInfo = BytecodeUtils.loadClassFile(className);
        System.out.println(classFileInfo);
    }
}
