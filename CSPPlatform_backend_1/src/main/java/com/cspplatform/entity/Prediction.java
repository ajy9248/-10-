package com.cspplatform.entity;

import java.util.Date;

public class Prediction {
    private String uid;
    private String type;
    private Date time;
    private String timeString;
    private String session;
    private String uname;
    private String grade;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    @Override
    public String toString() {
        return "Prediction{" +
                "uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", timeString='" + timeString + '\'' +
                ", session='" + session + '\'' +
                ", uname='" + uname + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    public Prediction(String uid, String type, Date time, String timeString, String session, String uname, String grade) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.timeString = timeString;
        this.session = session;
        this.uname = uname;
        this.grade = grade;
    }

    public Prediction(){}

}
