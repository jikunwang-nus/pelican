package com.charsmart.data.basic.keywords;

/**
 * @Author: Wonder
 * @Date: Created on 2023/4/17 15:27
 */
public class Volatile {
    private  int count;

    public void add() {
        count += 1;
    }

    int getValue() {
        return count;
    }

    public static void main(String[] args) {
        Volatile v = new Volatile();
        v.add();
        System.out.println(v.getValue());
    }
}
