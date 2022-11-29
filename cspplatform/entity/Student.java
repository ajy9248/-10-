package com.cspplatform.entity;

public class Student {
    private String uid;
    private String uname;
    private String grade;
    private Integer freeChance;

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

    public Integer getFreeChance() {
        return freeChance;
    }

    public void setFreeChance(Integer freeChance) {
        this.freeChance = freeChance;
    }

    public Student(){}

    public Student(String uid, String uname, String grade, Integer freeChance) {
        this.uid = uid;
        this.uname = uname;
        this.grade = grade;
        this.freeChance = freeChance;
    }

    @Override
    public String toString() {
        return "Student{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", grade='" + grade + '\'' +
                ", freeChance=" + freeChance +
                '}';
    }
}
