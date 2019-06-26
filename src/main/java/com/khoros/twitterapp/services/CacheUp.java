package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.List;

public class CacheUp {
    private HashMap<String, List<Status>> cacheMap = new HashMap<>();

    public HashMap<String, List<Status>> getTimelineCache() {
        return cacheMap;
    }

    public void addStatusToCache(String setType, List<Status> statusList) {

        cacheMap.put(setType, statusList);

    }

}