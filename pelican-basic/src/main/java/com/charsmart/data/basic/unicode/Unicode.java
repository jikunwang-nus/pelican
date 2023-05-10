package com.charsmart.data.basic.unicode;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/8 5:23 PM
 */
public class Unicode {
    public static void main(String[] args) {
        String s = "\uD834\uDD1E";
        System.out.println(s.length());
        System.out.println(s.codePointCount(0,s.length()));
        System.out.println(s.codePoints().count());
        System.out.println(s.getBytes());
        System.out.println(s);
    }
}
