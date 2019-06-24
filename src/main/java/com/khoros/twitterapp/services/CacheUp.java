package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheUp {
    private Set<Status> initialSet = new HashSet<>();
    private HashMap<TwitterService.CacheSetType, Set<Status>> cacheMap = new HashMap<>();

    public CacheUp() {
        cacheMap.put(TwitterService.CacheSetType.HOME, initialSet);
        cacheMap.put(TwitterService.CacheSetType.USER, initialSet);
    }

    public Set<Status> getTimelineCache(TwitterService.CacheSetType setType) {
        return cacheMap.get(setType);
    }

    public void addStatusToCache(TwitterService.CacheSetType setType, List<Status> statusList) {

        Set<Status> currentSet = cacheMap.get(setType);

        statusList.forEach(status -> currentSet.add(status));
        cacheMap.replace(setType,currentSet);

    }

}