package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.List;

public class CacheUp {
    private HashMap<TwitterService.CacheSetType, List<Status>> cacheMap = new HashMap<>();

    public List<Status> getTimelineCache(TwitterService.CacheSetType setType) {
        return cacheMap.get(setType);
    }

    public void addStatusToCache(TwitterService.CacheSetType setType, List<Status> statusList) {

        cacheMap.put(setType, statusList);

    }

}