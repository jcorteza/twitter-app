package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.List;

public class CacheUp {
    private HashMap<TwitterService.CacheListType, List<Status>> cacheMap = new HashMap<>();

    public HashMap<TwitterService.CacheListType, List<Status>> getTimelineCache() {
        return cacheMap;
    }

    public void addStatusToCache(TwitterService.CacheListType setType, List<Status> statusList) {

        cacheMap.put(setType, statusList);

    }

}