package com.khoros.twitterapp;

import com.khoros.twitterapp.services.CacheUp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import twitter4j.Status;
import java.util.HashSet;
import java.util.Set;

public class CacheUpTest {

    private CacheUp cacheUp = CacheUp.getInstance();
    private Set<Status> testSet;
    private Set<Status> originalSet;
    private twitter4j.Status testStatus;
    private String testKey1;
    private String testKey2;

    @Before
    public void setup() {

        originalSet = cacheUp.getCacheSet();
        testSet = new HashSet<>();
        testStatus = new Twitter4jStatusImpl();

        testKey1 = "test1";
        testKey2 = "test2";

    }

    @After
    public void resetMock() {

        cacheUp.setCacheSet(originalSet);

    }

    @Test
    public void cleanCacheTest() {

        testSet.add(testStatus);
        testSet.add(testStatus);

        cacheUp.setCacheSet(testSet);
        cacheUp.getCacheSet();

        Assert.assertTrue(testSet.contains(testKey1));
        Assert.assertFalse(testSet.contains(testKey2));
    }
}