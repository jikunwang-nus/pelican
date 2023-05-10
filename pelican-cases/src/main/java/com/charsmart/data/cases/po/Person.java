package com.charsmart.data.cases.po;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/12 3:31 PM
 */
public class Person {
    public String name;
    private int age;

    public void showName() {
        System.out.println(name);
    }

    @Getter
    @Setter
    static class Hero extends Person {
        public String location;
        private long id;

        public void hello() {
            System.out.println("hello");
        }
    }

    public static void main(String[] args) {
        Class<?> clazz = Hero.class;
        Field[] fields = clazz.getFields();
        Field[] declaredFields = clazz.getDeclaredFields();
        Constructor<?>[] constructors = clazz.getConstructors();
        ClassLoader classLoader = clazz.getClassLoader();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] methods = clazz.getMethods();
    }
}
