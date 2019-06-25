package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheUp {
    private HashMap<TwitterService.CacheSetType, Set<Status>> cacheMap = new HashMap<>();

    public CacheUp() {
        cacheMap.put(TwitterService.CacheSetType.HOME, new HashSet<>());
        cacheMap.put(TwitterService.CacheSetType.USER, new HashSet<>());
    }

    public Set<Status> getTimelineCache(TwitterService.CacheSetType setType) {
        System.out.println(setType);
        return cacheMap.get(setType);
    }

    public void addStatusToCache(TwitterService.CacheSetType setType, List<Status> statusList) {

        Set<Status> currentSet = cacheMap.get(setType);

        statusList.forEach(status -> currentSet.add(status));
        cacheMap.replace(setType, currentSet);

    }

}