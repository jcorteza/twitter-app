package com.khoros.twitterapp.models;

import java.util.Date;

public class Status {

    private long statusID;

    private String message;

    private User user;

    private Date createdAt;

    public void setStatusID(long id) {

        this.statusID = id;

    }

    public long getStatusID() {

        return statusID;

    }

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
