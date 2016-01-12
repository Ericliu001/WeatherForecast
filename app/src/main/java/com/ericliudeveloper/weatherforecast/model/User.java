package com.ericliudeveloper.weatherforecast.model;

/**
 * Created by ericliu on 11/07/15.
 */
public class User {
    private String name;
    private String sex;
    private int age = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
