package com.mildlamb.pojo;

import org.springframework.stereotype.Component;

// 注解的方式定义bean
@Component("kindred")
public class Kindred {

    private int age;

    public Kindred() {
    }

    public Kindred(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Kindred{" +
                "age=" + age +
                '}';
    }
}
