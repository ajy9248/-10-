package com.cspplatform.entity;

public class Student {
    private String uid;
    private String uname;
    private String grade;
    private Integer freechance;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getFreechance() {
        return freechance;
    }

    public void setFreechance(Integer freechance) {
        this.freechance = freechance;
    }

    public Student(){}

    public Student(String uid, String uname, String grade, Integer freechance) {
        this.uid = uid;
        this.uname = uname;
        this.grade = grade;
        this.freechance = freechance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", grade='" + grade + '\'' +
                ", freeChance=" + freechance +
                '}';
    }
}
