package com.cspplatform.entity;

public class Teacher {
    private String uid;
    private String uname;
    private String identity;

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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Teacher(){}

    public Teacher(String uid, String uname, String identity) {
        this.uid = uid;
        this.uname = uname;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
