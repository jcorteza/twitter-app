package com.khoros.twitterapp.services;

import twitter4j.Status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheUp {
    // clean up interval 1 week
    private Set<Status> homeTimelineSet = new HashSet<>();
    private Set<Status> userTimelineSet = new HashSet<>();

    public Set<Status> getHomeTimelineSet() {
        return homeTimelineSet;
    }

    public Set<Status> getUserTimelineSet() {
        return userTimelineSet;
    }

    public void addToHomeTimelineSet(List<Status> statusList) {
        statusList.forEach(status -> homeTimelineSet.add(status));
    }

    public void addToUserTimelineSet(List<Status> statusList) {
        statusList.forEach(status -> userTimelineSet.add(status));
    }

}