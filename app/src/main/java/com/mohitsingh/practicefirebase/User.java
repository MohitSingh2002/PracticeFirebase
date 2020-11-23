package com.mohitsingh.practicefirebase;

public class User {

    public String name, city, state;
    public int age;

    public User() {
    }

    public User(String name, String city, String state, int age) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age +
                '}';
    }

}
