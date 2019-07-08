package com.khoros.twitterapp.models;

public class User {

    private long userID;

    private String twHandle;

    private String name;

    private String profileImageUrl;

    public void setUserID(long id) {

        this.userID = id;

    }

    public long getUserID() {

        return userID;

    }

    public void setTwHandle(String twHandle) {

        this.twHandle = twHandle;

    }

    public String getTwHandle() {

        return twHandle;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getName() {

        return name;

    }

    public void setProfileImageUrl(String url) {

        this.profileImageUrl = url;

    }

    public String getProfileImageUrl() {

        return profileImageUrl;

    }
}
