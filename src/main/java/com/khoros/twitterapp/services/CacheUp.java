package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.models.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CacheUp {
    // interval 1 week
    private static final long CLEAN_UP_INTERVAL = 7 * 24 * 60 * 60 * 1000;
    private static HashMap<String, CacheStatus> cacheStatusHashMap = new HashMap<>();
    private static List<Status> cacheStatusList = new ArrayList<>();

    public static HashMap<String, CacheStatus> getCacheStatusHashMap() {
        return cacheStatusHashMap;
    }

    public static List<Status> getCacheStatusList() {
        return cacheStatusList;
    }

    public static void addStatusToCache(Status status) {
        String cacheStatusKey = new StringBuilder()
                .append(status.getCreatedAt())
                .append("-")
                .append(status.getUser().getTwHandle())
                .append("-")
                .append(status.getMessage())
                .toString();
        cacheStatusHashMap.putIfAbsent(
                cacheStatusKey,
                new CacheStatus(new Date(), status)
        );
    }
}
