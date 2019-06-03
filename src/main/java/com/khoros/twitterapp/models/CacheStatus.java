package com.khoros.twitterapp.models;

import java.util.Date;

public class CacheStatus {
    private Date cacheObjectCreated;
    private Status statusObject;

    public CacheStatus(Date cacheObjectCreated, Status statusObject) {
        this.cacheObjectCreated = cacheObjectCreated;
        this.statusObject = statusObject;
    }

    public Status  getStatus() {
        return statusObject;
    }
    public Date getCacheObjectCreated() {
        return cacheObjectCreated;
    }
}
