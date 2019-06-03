package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RunnableCache implements Runnable {

    public long interval;

    public RunnableCache(long interval) {
        this.interval = interval;
    }

    public RunnableCache() {
        interval = CacheUp.CLEAN_UP_INTERVAL;
    }

    @Override
    public void run() {

        Date now = new Date();
        Set<Map.Entry<String, CacheStatus>> cachStatusSet = CacheUp.getInstance().getCacheStatusHashMap().entrySet();

        for(Iterator<Map.Entry<String, CacheStatus>> i = cachStatusSet.iterator(); i.hasNext();) {
            Map.Entry<String, CacheStatus> cacheStatusEntry = i.next();
            long dateCreatedCacheStatus = cacheStatusEntry.getValue().getCacheObjectCreated().getTime();

            if(now.getTime() - dateCreatedCacheStatus >= interval) {
                i.remove();
            }
        }
    }

}
