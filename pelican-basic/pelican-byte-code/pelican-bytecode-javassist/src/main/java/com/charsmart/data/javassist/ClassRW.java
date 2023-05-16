package com.charsmart.data.javassist;

import javassist.ClassPool;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;

import java.io.FileOutputStream;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/15 4:28 PM
 */
public class ClassRW {
    private static final String classPath = "./target/classes/com/charsmart/data/javassist/Simple.class";

    public void createClass() {
        ClassFile cf = new ClassFile(false, "com.charsmart.data.javassist.Simple", null);
        cf.setAccessFlags(AccessFlag.PUBLIC);
        try {
            FieldInfo fd = new FieldInfo(cf.getConstPool(), "name", "Ljava.lang.String;");
            fd.setAccessFlags(AccessFlag.PRIVATE);
            cf.addField(fd);
            MethodInfo md = new MethodInfo(cf.getConstPool(), "print", "()V");
            cf.addMethod(md);
            ClassPool classPool = ClassPool.getDefault();
            byte[] bytes = classPool.makeClass(cf).toBytecode();
            try (FileOutputStream outputStream = new FileOutputStream(classPath)) {
                outputStream.write(bytes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void modify() {
        ClassPool cp = ClassPool.getDefault();
        try {
            ClassFile cf = cp.get("com.charsmart.data.javassist.Simple").getClassFile();
            FieldInfo fd = new FieldInfo(cf.getConstPool(), "code", "Ljava.lang.String;");
            fd.setAccessFlags(AccessFlag.PRIVATE);
            cf.addField(fd);
            /*添加init*/
            MethodInfo methodInfo = new MethodInfo(cf.getConstPool(), MethodInfo.nameInit, "()V");
            Bytecode bytecode = new Bytecode(cf.getConstPool());
            bytecode.addAload(0);
            bytecode.addInvokespecial("java.lang.Object", MethodInfo.nameInit, "()V");
            bytecode.addReturn(null);
            methodInfo.setCodeAttribute(bytecode.toCodeAttribute());
            cf.addMethod(methodInfo);
            byte[] bytes = cp.makeClass(cf).toBytecode();
            try (FileOutputStream outputStream = new FileOutputStream(classPath)) {
                outputStream.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
