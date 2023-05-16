package com.charsmart.data.asm;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/14 3:01 PM
 */
public class Simple extends Model implements Movable, Printable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void move() {
        System.out.println("start proxy...");
        System.out.println("move");
        System.out.println("end proxy...");
    }

    @Override
    public void print() {
        System.out.println("print");
    }

    public static void main(String[] args) {
        System.out.println("main");
    }
}
