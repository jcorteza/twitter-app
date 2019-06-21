package com.khoros.twitterapp;

import com.khoros.twitterapp.services.CacheUp;
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
         testCacheUp.addToHomeTimelineSet(testList);
         testCacheUp.addToUserTimelineSet(testList);

    }

    @Test
    public void addToHomeTimelineSetTest() {

        Assert.assertTrue(testCacheUp.getHomeTimelineSet().contains(testList.get(0)));
        Assert.assertTrue(testCacheUp.getHomeTimelineSet().contains(testList.get(1)));
        Assert.assertTrue(testCacheUp.getHomeTimelineSet().contains(testList.get(2)));
    }

    @Test
    public void addToUserTimelineSetTest() {

        Assert.assertTrue(testCacheUp.getUserTimelineSet().contains(testList.get(0)));
        Assert.assertTrue(testCacheUp.getUserTimelineSet().contains(testList.get(1)));
        Assert.assertTrue(testCacheUp.getUserTimelineSet().contains(testList.get(2)));
    }
}
