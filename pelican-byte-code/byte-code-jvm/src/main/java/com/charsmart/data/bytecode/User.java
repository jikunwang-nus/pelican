package com.charsmart.data.bytecode;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 5:40 PM
 */
public class User extends Model implements Moveable {
    private String name;
    private Boolean adult;

    private Integer age;

    @Override
    public void move() {
        System.out.println("user move");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdult() {
        return adult;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
        adult = age >= 18;
    }

    public void grow() {
        synchronized (this) {
            try {
                this.age++;
                if (this.age >= 18) {
                    adult = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("grow count + 1");
            }
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("wonder");
        user.setAge(15);
        while (!user.getAdult()) {
            user.grow();
        }
        System.out.println(user.getName() + " has already been adult!");
    }
}
