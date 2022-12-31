package com.cspplatform.entity;

public class Score {
    private String uid;
    private Integer score;
    private String session;
    private String uname;
    private String grade;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Score(){}

    public Score(String uid, Integer score, String session, String uname, String grade) {
        this.uid = uid;
        this.score = score;
        this.session = session;
        this.uname = uname;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Score{" +
                "uid='" + uid + '\'' +
                ", score=" + score +
                ", session='" + session + '\'' +
                ", uname='" + uname + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
