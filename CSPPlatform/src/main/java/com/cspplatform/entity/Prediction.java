package com.cspplatform.entity;

import java.util.Date;

public class Prediction {
    private String uid;
    private String type;
    private Date time;
    private String timeString;

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

    public Prediction(String uid, String type, Date time, String timeString) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.timeString = timeString;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", timeString='" + timeString + '\'' +
                '}';
    }

    public Prediction(){}

}
