package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;
import twitter4j.Status;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class CacheUp {
    // clean up interval 1 week
    public static final long CLEAN_UP_INTERVAL = 7 * 24 * 60 * 60 * 1000;
    private HashMap<String, CacheStatus> cacheStatusHashMap = new HashMap<>();
    private Set<Status> cacheStatusSet = new HashSet<>();
    private static CacheUp instance = new CacheUp();

    private CacheUp() {
        //private constructor
    }

    public static CacheUp getInstance() {
        return instance;
    }

    public HashMap<String, CacheStatus> getCacheStatusHashMap() {
        return cacheStatusHashMap;
    }

    public Set<Status> getCacheStatusSet() {

        Date now = new Date();
        Set<Map.Entry<String, CacheStatus>> cacheStatusEntries = getCacheStatusHashMap().entrySet();

        cacheStatusSet.clear();

        for(Iterator<Map.Entry<String, CacheStatus>> i = cacheStatusEntries.iterator(); i.hasNext();) {
            Map.Entry<String, CacheStatus> cacheStatusEntry = i.next();
            long dateCreatedCacheStatus = cacheStatusEntry.getValue().getCacheObjectCreated().getTime();

            if(now.getTime() - dateCreatedCacheStatus >= CLEAN_UP_INTERVAL) {

                i.remove();

            } else {

                cacheStatusSet.add(cacheStatusEntry.getValue().getStatus());

            }
        }

        return cacheStatusSet;
    }

    public void addStatusToCache(twitter4j.Status status) {
        String cacheStatusKey = new StringBuilder()
                .append(status.getCreatedAt())
                .append("-")
                .append(status.getUser().getScreenName())
                .toString();
        cacheStatusHashMap.putIfAbsent(
                cacheStatusKey,
                new CacheStatus(status)
        );
    }

    public void setCacheStatusHashMap(HashMap<String, CacheStatus> hashMap) {
        cacheStatusHashMap = hashMap;
    }
}