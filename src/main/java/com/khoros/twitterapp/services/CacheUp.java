package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheUp {
    // clean up interval 1 week
    private Set<Status> cacheSet = new HashSet<>();

    public Set<Status> getCacheSet() {
        return cacheSet;
    }

    public void addStatusesToCache(List<Status> statusList) {
        statusList.forEach(status -> cacheSet.add(status));
    }

}