package com.cspplatform.entity;

public class Login {
    private String uid;
    private String pwd;
    private String type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Login(){}

    public Login(String uid, String pwd, String type) {
        this.uid = uid;
        this.pwd = pwd;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Login{" +
                "uid='" + uid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
