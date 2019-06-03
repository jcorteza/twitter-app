package com.khoros.twitterapp;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;
import com.khoros.twitterapp.services.CacheUp;

import com.khoros.twitterapp.services.RunnableCache;
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
    private Runnable originalRunnable;
    private long testInterval;
    private User newUser;
    private Status newStatus;
    private String testKey1;
    private String testKey2;

    @Before
    public void setup() {


        originalRunnable = cacheUp.getCleanCache();
        // test interval for clean up — 5 minutes
        testInterval = 300000;
        originalHashMap = cacheUp.getCacheStatusHashMap();
        testCacheMap = new HashMap<>();
        twitter4j.Status testStatus = new Twitter4jStatusImpl();

        newUser = new User();
        newUser.setTwHandle(testStatus.getUser().getScreenName());
        newUser.setName(testStatus.getUser().getName());
        newUser.setProfileImageUrl(testStatus.getUser().getProfileImageURL());

        newStatus = new Status();
        newStatus.setMessage(testStatus.getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(testStatus.getCreatedAt());

        testKey1 = "test1";
        testKey2 = "test2";

    }

    @After
    public void resetMock() {

        cacheUp.setCacheStatusHashMap(originalHashMap);
        cacheUp.setCleanCache(originalRunnable);

    }

    @Test
    public void cleanCacheTest() {

        Date testDate = new Date();
        long timeDiff10Min = 10 * 60 * 1000;
        testCacheMap.putIfAbsent(testKey2, new CacheStatus(new Date(testDate.getTime() - timeDiff10Min), newStatus));
        testCacheMap.putIfAbsent(testKey1, new CacheStatus(testDate, newStatus));

        cacheUp.setCacheStatusHashMap(testCacheMap);

        cacheUp.setCleanCache(new RunnableCache(testInterval));
        cacheUp.getCleanCache().run();

        Assert.assertTrue(testCacheMap.keySet().contains(testKey1));
        Assert.assertFalse(testCacheMap.keySet().contains(testKey2));
    }
}