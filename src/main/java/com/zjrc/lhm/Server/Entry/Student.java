package com.zjrc.lhm.Server.Entry;

public class Student {

    private Integer id;
    private String name;
    private String sex;
    private String address;
    private String email;
    private String hobby;
    private String phone;
    private Integer score;
    private Integer age;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", hobby='" + hobby + '\'' +
                ", phone='" + phone + '\'' +
                ", score=" + score +
                ", age=" + age +
                '}';
    }

    public Student(String name, Integer age, String sex, String address, String email, String hobby, String phone, Integer score) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.hobby = hobby;
        this.phone = phone;
        this.score = score;

    }


    public Student() {
    }
}
