package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.List;

public class CacheUp {
    private HashMap<TwitterService.CacheListType, List<Status>> cacheMap = new HashMap<>();

    public List<Status> getTimelineCache(TwitterService.CacheListType setType) {
        return cacheMap.get(setType);
    }

    public void addStatusToCache(TwitterService.CacheListType setType, List<Status> statusList) {

        cacheMap.put(setType, statusList);

    }

}