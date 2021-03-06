package com.khoros.twitterapp.models;

import java.util.Date;

public class Status {

    private String statusID;

    private String message;

    private User user;

    private Date createdAt;

    private String postUrl;

    public void setStatusID(String id) {

        this.statusID = id;

    }

    public String getStatusID() {

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

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }
}
