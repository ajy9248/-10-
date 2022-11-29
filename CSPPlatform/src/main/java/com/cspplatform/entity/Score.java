package com.cspplatform.entity;

public class Score {
    private String uid;
    private Integer score;
    private String session;

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

    public Score(){}

    public Score(String uid, Integer score, String session) {
        this.uid = uid;
        this.score = score;
        this.session = session;
    }

    @Override
    public String toString() {
        return "Score{" +
                "uid='" + uid + '\'' +
                ", score=" + score +
                ", session='" + session + '\'' +
                '}';
    }
}
