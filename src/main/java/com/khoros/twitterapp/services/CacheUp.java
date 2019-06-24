package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CacheUp {
//    private Set<Status> homeTimelineSet = new HashSet<>();
//    private Set<Status> userTimelineSet = new HashSet<>();
    private HashMap<String, Set<Status>> cacheMap = new HashMap<>();

    public Set<Status> getTimelineSet(String setType) {
        return cacheMap.get(setType);
    }

    public void addStatusToCache(String setType, List<Status> statusList) {
        Set<Status> currentSet = cacheMap.get(setType);

        statusList.forEach(status -> currentSet.add(status));
        cacheMap.replace(setType,currentSet);
    }

}