package com.charsmart.pelican.basic.lang.reflect;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Optional;

/**
 * @Author: Wonder
 * @Date: Created on 2022/7/13 5:28 PM
 */
public class User extends Model implements Serializable, Comparable<Integer> {
    private int age;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

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

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.charsmart.pelican.basic.lang.reflect.User");
        /*super class and interfaces*/
        Type genericSuperclass = clazz.getGenericSuperclass();
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        Annotation[] annotations = clazz.getAnnotations();

        /*constructor and new instance*/
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> constructor = clazz.getConstructor(String.class);
        User user = (User) constructor.newInstance("wonder");
        user.setAge(18);

        /*method and method invoke*/
        Method[] methods = clazz.getMethods();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method method = clazz.getMethod("getName");
        String name = (String) method.invoke(user);
        Method setMethod = clazz.getMethod("setName", String.class);
        setMethod.invoke(user, "jerry");

        /*fields*/
        Field[] fields = clazz.getFields();
        Field[] declaredFields = clazz.getDeclaredFields();
        Field field = clazz.getDeclaredField("age");
        Object o = field.get(user);
        field.setAccessible(true);
        field.set(user, 20);
        Field nameField = clazz.getDeclaredField("name");
        String uname = (String) nameField.get(user);

        Optional<String> s = Optional.ofNullable("12");
        Optional<String> s1 = s.flatMap(t -> Optional.of(t.toLowerCase(Locale.ROOT)));
        Optional<String> s2 = s.map(t -> t.toLowerCase(Locale.ROOT));

    }

    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}
