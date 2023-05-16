package com.charsmart.data.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_8;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/14 2:46 PM
 */
public class ClassRW {
    private static final String className = "Simple.class";

    public void modifyClass() {
        File file = new File(className);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            ClassReader classReader = new ClassReader(inputStream);
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
            classReader.accept(new ProxyVisitor(classWriter), ClassReader.SKIP_DEBUG);
            byte[] bytes = classWriter.toByteArray();
            try (FileOutputStream os = new FileOutputStream(file)) {
                os.write(bytes);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createClass() {
        ClassWriter cw = new ClassWriter(0);
        /*创建class*/
        cw.visit(V1_8, ACC_PUBLIC,
                "com.charsmart.data.asm/Simple", null
                , "com.charsmart.data.asm/Model",
                new String[]{"com.charsmart.data.asm/Movable", "com.charsmart.data.asm/Printable"});

        cw.visitField(ACC_PRIVATE, "name", "Ljava/lang/String;", null, null);
        cw.visitField(ACC_PRIVATE, "age", "I", null, 0);

        /*编写move方法*/
        asmMoveMethod(cw);
        /*编写print方法*/
        asmPrintMethod(cw);
        /*编写main方法*/
        asmMainMethod(cw);
        cw.visitEnd();
        byte[] bytes = cw.toByteArray();
        try (FileOutputStream os = new FileOutputStream(className)) {
            os.write(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void asmMainMethod(ClassWriter cw) {
        MethodVisitor methodVisitor = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitParameter("args", 0);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(40, label0);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("main");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(41, label1);
        methodVisitor.visitInsn(RETURN);
        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label2, 0);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

    private void asmPrintMethod(ClassWriter cw) {
        MethodVisitor methodVisitor = cw.visitMethod(ACC_PUBLIC, "print", "()V", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(34, label0);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("print");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(35, label1);
        methodVisitor.visitInsn(RETURN);
        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLocalVariable("this", "Lcom/charsmart/data/asm/Simple;", null, label0, label2, 0);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

    private void asmMoveMethod(ClassWriter cw) {
        MethodVisitor methodVisitor = cw.visitMethod(ACC_PUBLIC, "move", "()V", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(29, label0);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodVisitor.visitLdcInsn("move");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(30, label1);
        methodVisitor.visitInsn(RETURN);
        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLocalVariable("this", "Lcom/charsmart/data/asm/Simple;", null, label0, label2, 0);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

}
