package com.real.hadi.addcontact;

import java.util.ArrayList;

public class BookListLayout {
    private String id;
    private String coverId;
    public String title;
    public String detail;
    public String userId;
    public String fullname;
    public String token;
    ;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public String getCoverId() {
        return coverId;
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
}

