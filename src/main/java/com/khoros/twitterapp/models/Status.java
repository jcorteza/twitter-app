package com.khoros.twitterapp.models;

import java.util.Date;

public class Status {

    private String message;

    private User user;

    private Date createdAt;

    public void setMessage(String message) {

        this.message = message;

    }

    public String getMessage() {

        return message;

    }

    public void setUser(User user) {

        this.user = user;

    }

    public User getUser() {

        return user;

    }

    public void setCreatedAt(Date date) {

        this.createdAt = date;

    }

    public Date getCreatedAt() {

        return createdAt;
        
    }
}
