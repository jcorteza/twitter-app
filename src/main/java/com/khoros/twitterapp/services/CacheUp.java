package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheUp {
    private HashMap<TwitterService.CacheSetType, Set<Status>> cacheMap = new HashMap<>();

    public Set<Status> getTimelineSet(TwitterService.CacheSetType setType) {
        return cacheMap.get(setType);
    }

    public void addStatusToCache(TwitterService.CacheSetType setType, List<Status> statusList) {

        Set<Status> currentSet;

        if(cacheMap.containsKey(setType)) {

            currentSet = cacheMap.get(setType);

            statusList.forEach(status -> currentSet.add(status));
            cacheMap.replace(setType,currentSet);

        } else {

            currentSet = new HashSet<>();

            statusList.forEach(status -> currentSet.add(status));
            cacheMap.put(setType, currentSet);
        }

    }

}