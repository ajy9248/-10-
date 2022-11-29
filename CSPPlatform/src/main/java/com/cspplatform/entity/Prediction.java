package com.cspplatform.entity;

import java.util.Date;

public class Prediction {
    private String uid;
    private String type;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Prediction(){}

    public Prediction(String uid, String type, Date date) {
        this.uid = uid;
        this.type = type;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
