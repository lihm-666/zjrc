package com.zjrc.lhm.Server.Entry;

public class Student {

    private String name;
    private String sex;
    private String address;
    private String email;
    private String hobby;
    private String phone;
    private int score;
    private int age;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", hobby='" + hobby + '\'' +
                ", phone='" + phone + '\'' +
                ", score=" + score +
                ", age=" + age +
                '}';
    }

    public Student(String name, String sex, String address, String email, String hobby, String phone, int score, int age) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.hobby = hobby;
        this.phone = phone;
        this.score = score;
        this.age = age;
    }

    public Student() {
    }
}
