package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.models.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public final class CacheUp {
    // interval 1 week
    public static final long CLEAN_UP_INTERVAL = 7 * 24 * 60 * 60 * 1000;
    private HashMap<String, CacheStatus> cacheStatusHashMap = new HashMap<>();
    private List<Status> cacheStatusList = new ArrayList<>();
    private Runnable cleanCache = new RunnableCache();
    private static CacheUp instance = new CacheUp();


    private CacheUp() {
        // private creator
    }

    public static CacheUp getInstance() {
        return instance;
    }

    public HashMap<String, CacheStatus> getCacheStatusHashMap() {
        return cacheStatusHashMap;
    }

    public List<Status> getCacheStatusList() {
        return cacheStatusList;
    }

    public void addStatusToCache(Status status) {
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

    public void setCacheStatusHashMap(HashMap<String, CacheStatus> hashMap) {
        cacheStatusHashMap = hashMap;
    }

    public Runnable getCleanCache() {
        return cleanCache;
    }

    public void setCleanCache(Runnable runnable) {
        cleanCache = runnable;
    }
}