package com.twu.person;

/**
 * @author yuan
 * @create 2020-09-07 11:22
 */
public class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
