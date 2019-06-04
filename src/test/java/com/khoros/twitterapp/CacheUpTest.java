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
         testCacheUp.addStatusesToCache(testList);
    }

    @Test
    public void addStatusToCacheTest() {

        Assert.assertTrue(testCacheUp.getCacheSet().contains(testList.get(0)));
        Assert.assertTrue(testCacheUp.getCacheSet().contains(testList.get(1)));
        Assert.assertTrue(testCacheUp.getCacheSet().contains(testList.get(2)));
    }
}
