package com.khoros.twitterapp;

import com.khoros.twitterapp.services.CacheUp;
import com.khoros.twitterapp.services.TwitterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

public class CacheUpTest {

    private CacheUp testCacheUp;
    private List<Status> testList;

    @Before
    public void setup() {
         testCacheUp = new CacheUp();
         testList = new ArrayList<>();
         testList.add(new Twitter4jStatusImpl());
         testList.add(new Twitter4jStatusImpl());
         testList.add(new Twitter4jStatusImpl());
         testCacheUp.addStatusToCache("home", testList);
         testCacheUp.addStatusToCache("user", testList);

    }

    @Test
    public void addStatusToCacheTest() {

        Assert.assertTrue(testCacheUp.getTimelineCache().get("home").contains(testList.get(0)));
        Assert.assertTrue(testCacheUp.getTimelineCache().get("home").contains(testList.get(1)));
        Assert.assertTrue(testCacheUp.getTimelineCache().get("home").contains(testList.get(2)));
        Assert.assertTrue(testCacheUp.getTimelineCache().get("user").contains(testList.get(0)));
        Assert.assertTrue(testCacheUp.getTimelineCache().get("user").contains(testList.get(1)));
        Assert.assertTrue(testCacheUp.getTimelineCache().get("user").contains(testList.get(2)));
    }

}
