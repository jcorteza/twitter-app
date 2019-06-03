package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.models.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CacheUp {
    // interval 1 week
    public static final long CLEAN_UP_INTERVAL = 7 * 24 * 60 * 60 * 1000;
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

    public static Runnable cleanCache = new Runnable() {
        @Override
        public void run() {

            Date now = new Date();
            Set<Map.Entry<String, CacheStatus>> cachStatusSet = getCacheStatusHashMap().entrySet();


            for(Iterator<Map.Entry<String, CacheStatus>> i = cachStatusSet.iterator(); i.hasNext();) {
                Map.Entry<String, CacheStatus> cacheStatusEntry = i.next();
                long dateCreatedCacheStatus = cacheStatusEntry.getValue().getCacheObjectCreated().getTime();

                if(now.getTime() - dateCreatedCacheStatus >= CLEAN_UP_INTERVAL) {
                    i.remove();
                }
            }

        }
    };

    public static void setCacheStatusHashMap(HashMap<String, CacheStatus> hashMap) {
        cacheStatusHashMap = hashMap;
    }
}