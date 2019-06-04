package com.khoros.twitterapp;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.services.CacheUp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.Date;
import java.util.HashMap;

public class CacheUpTest {

    private CacheUp cacheUp = CacheUp.getInstance();
    private HashMap<String, CacheStatus> testCacheMap;
    private HashMap<String, CacheStatus> originalHashMap;
    private twitter4j.Status testStatus;
    private String testKey1;
    private String testKey2;

    @Before
    public void setup() {

        originalHashMap = cacheUp.getCacheStatusHashMap();
        testCacheMap = new HashMap<>();
        testStatus = new Twitter4jStatusImpl();

        testKey1 = "test1";
        testKey2 = "test2";

    }

    @After
    public void resetMock() {

        cacheUp.setCacheStatusHashMap(originalHashMap);

    }

    @Test
    public void cleanCacheTest() {

        Date testDate = new Date();

        testCacheMap.putIfAbsent(testKey2, new CacheStatus(new Date(testDate.getTime() - CacheUp.CLEAN_UP_INTERVAL), testStatus));
        testCacheMap.putIfAbsent(testKey1, new CacheStatus(testDate, testStatus));

        cacheUp.setCacheStatusHashMap(testCacheMap);
        cacheUp.getCacheStatusSet();

        Assert.assertTrue(testCacheMap.keySet().contains(testKey1));
        Assert.assertFalse(testCacheMap.keySet().contains(testKey2));
    }
}