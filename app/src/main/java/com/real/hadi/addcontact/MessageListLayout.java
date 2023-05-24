package com.real.hadi.addcontact;

import java.util.ArrayList;

public class MessageListLayout {
    private String id;
    public String title;
    public String detail;
    public String userId;
    public String fullname;
    public String token;
    public String priority;
    public ArrayList<String> reads;

    ;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setReads(ArrayList<String> reads) {
        this.reads = reads;
    }

    public ArrayList<String> getReads() {return reads;}

}

