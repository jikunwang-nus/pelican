package com.charsmart.data.bytecode;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 5:59 PM
 */
public class BytecodeUtils {
    public static ClassFileInfo loadClassFile(String fileName) {
        ;
        try (DataInputStream is = new DataInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return new ClassFileInfo().init(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String bytecodeInfo(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] ba = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (true) {
                int read = fis.read(ba);
                if (read < 1024) {
                    sb.append(new String(ba, StandardCharsets.UTF_8));
                    break;
                } else {
                    ba = new byte[1024];
                }
            }
            return sb.toString();
        }
    }
}
