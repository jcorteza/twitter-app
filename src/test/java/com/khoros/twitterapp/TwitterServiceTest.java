package com.khoros.twitterapp;

import com.khoros.twitterapp.services.CacheUp;
import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.models.Status;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.services.TwitterServiceException;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.mockito.InjectMocks;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TwitterServiceTest {

    // private Configuration originalConfig;
    private CacheUp cacheUp;
    @InjectMocks private TwitterService twSingleton;
    private Twitter mockFactory;
    private String testStatusText;
    private ResponseList<twitter4j.Status> twResponse;
    private twitter4j.Status exampleStatus;
    private Set<twitter4j.Status> originalCacheSet;

    @Before
    public void setup() {

        cacheUp = mock(CacheUp.class);
        twSingleton = TwitterService.getInstance();
        mockFactory = mock(Twitter.class);
        twSingleton.setTWFactory(mockFactory);
        twSingleton.setCacheUp(cacheUp);
        testStatusText = "Tweet Test";
        twResponse = new ResponseImplTest<>();
        exampleStatus = new Twitter4jStatusImpl();
        twResponse.add(exampleStatus);
        originalCacheSet = cacheUp.getCacheSet();

    }

    @After
    public void resetCacheUp() {
        reset(cacheUp);
    }

    @Test
    public void updateStatusTestSuccess() {

        Optional<Status> serviceResponse = Optional.empty();

        try {

            when(mockFactory.updateStatus(testStatusText)).thenReturn(exampleStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception");

        } catch (TwitterServiceException e) {

            Assert.fail("Test failed due to Twitter Service Exception.");

        }

        Assert.assertEquals(exampleStatus.getText(), serviceResponse.get().getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), serviceResponse.get().getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), serviceResponse.get().getUser().getProfileImageUrl());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), serviceResponse.get().getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getName(), serviceResponse.get().getUser().getName());

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        Optional<List<Status>> responseList = Optional.empty();

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getHomeTimeline();

        } catch (Exception e) {

            Assert.fail("Test failed due to Twitter Exception");

        }

        Assert.assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getName(), responseList.get().get(0).getUser().getName());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), responseList.get().get(0).getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), responseList.get().get(0).getUser().getProfileImageUrl());

    }

    @Test
    public void getCachedTimelineTest() {

        Set<twitter4j.Status> testSet = new HashSet<>();
        Optional<List<Status>> testList = null;

        for (int i = 0; i < 3; i++) {
            testSet.add(new Twitter4jStatusImpl());
        }

        try {

            when(cacheUp.getCacheSet()).thenReturn(testSet);
            testList = twSingleton.getHomeTimeline();

        } catch(TwitterServiceException e) {

            Assert.fail("TwitterService unit test failed due to TwitterServiceException.");

        }

        Assert.assertTrue(testList.get().size() == 3);
    }

    @Test
    public void getFactoryTest() {

        Assert.assertEquals(mockFactory, TwitterService.getInstance().getTwitterFactory());

    }

    @Test
    public void setCacheUpTest() {

        CacheUp testCacheUp = new CacheUp();
        twSingleton.setCacheUp(testCacheUp);

        Assert.assertEquals(testCacheUp.getCacheSet(), twSingleton.getCacheUp().getCacheSet());
    }

}
