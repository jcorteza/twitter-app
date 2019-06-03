package com.khoros.twitterapp.services;

import com.khoros.twitterapp.models.CacheStatus;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RunnableCache implements Runnable {

    public Date now;
    public long interval;


    public RunnableCache(Date date, long interval) {
        now = date;
        this.interval = interval;
    }

    public RunnableCache() {
        now = new Date();
        interval = CacheUp.CLEAN_UP_INTERVAL;
    }

    @Override
    public void run() {
        Set<Map.Entry<String, CacheStatus>> cachStatusSet = CacheUp.getCacheStatusHashMap().entrySet();


        for(Iterator<Map.Entry<String, CacheStatus>> i = cachStatusSet.iterator(); i.hasNext();) {
            Map.Entry<String, CacheStatus> cacheStatusEntry = i.next();
            long dateCreatedCacheStatus = cacheStatusEntry.getValue().getCacheObjectCreated().getTime();

            if(now.getTime() - dateCreatedCacheStatus >= interval) {
                i.remove();
            }
        }
    }

}
