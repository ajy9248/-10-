package com.cspplatform.entity;

public class Login {
    private String uid;
    private String pwd;
    private String type;
    private String sessionFlag;
    private Integer freeChance;

    public Login(){}

    public Login(String uid, String pwd, String type, String sessionFlag, Integer freeChance) {
        this.uid = uid;
        this.pwd = pwd;
        this.type = type;
        this.sessionFlag = sessionFlag;
        this.freeChance = freeChance;
    }

    public Integer getFreeChance() {
        return freeChance;
    }

    public void setFreeChance(Integer freeChance) {
        this.freeChance = freeChance;
    }

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

    public String getSessionFlag() {
        return sessionFlag;
    }

    public void setSessionFlag(String sessionFlag) {
        this.sessionFlag = sessionFlag;
    }

    @Override
    public String toString() {
        return "Login{" +
                "uid='" + uid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", type='" + type + '\'' +
                ", sessionFlag='" + sessionFlag + '\'' +
                ", freeChance=" + freeChance +
                '}';
    }
}
