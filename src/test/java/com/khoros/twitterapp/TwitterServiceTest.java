package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;
import com.khoros.twitterapp.services.CacheUp;
import com.khoros.twitterapp.models.Status;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.Before;
import twitter4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TwitterServiceTest {

    private CacheUp cacheUp;
    private TwitterService twSingleton;
    private Twitter mockFactory;
    private String testStatusText;
    private String testUrl;
    private ResponseList<twitter4j.Status> twResponse;
    private twitter4j.Status exampleStatus;

    @Before
    public void setup() {

        mockFactory = mock(Twitter.class);
        twSingleton = new TwitterService(mockFactory);
        cacheUp = mock(CacheUp.class);
        twSingleton.setCacheUp(cacheUp);
        testStatusText = "Tweet Test";
        twResponse = new ResponseImplTest<>();
        exampleStatus = new Twitter4jStatusImpl();
        testUrl = new StringBuilder()
            .append("https://twitter.com/")
            .append(exampleStatus.getUser().getScreenName())
            .append("/status/")
            .append(exampleStatus.getId())
            .toString();
        twResponse.add(exampleStatus);

    }

    @Test
    public void updateStatusTestSuccess() {

        Optional<Status> serviceResponse = Optional.empty();

        try {

            when(mockFactory.updateStatus(testStatusText)).thenReturn(exampleStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            fail("Test failed due to Twitter Exception");

        } catch (TwitterServiceException e) {

            fail("Test failed due to Twitter Service Exception.");

        }

        assertEquals(exampleStatus.getText(), serviceResponse.get().getMessage());
        assertEquals(exampleStatus.getCreatedAt(), serviceResponse.get().getCreatedAt());
        assertEquals(exampleStatus.getUser().get400x400ProfileImageURL(), serviceResponse.get().getUser().getProfileImageUrl());
        assertEquals(exampleStatus.getUser().getScreenName(), serviceResponse.get().getUser().getTwHandle());
        assertEquals(exampleStatus.getUser().getName(), serviceResponse.get().getUser().getName());
        assertEquals(testUrl, serviceResponse.get().getPostUrl());

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        Optional<List<Status>> responseList = null;

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getHomeTimeline();

        } catch (Exception e) {

            fail("Test failed due to Twitter Exception");

        }

        assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        assertEquals(exampleStatus.getUser().getName(), responseList.get().get(0).getUser().getName());
        assertEquals(exampleStatus.getUser().getScreenName(), responseList.get().get(0).getUser().getTwHandle());
        assertEquals(exampleStatus.getUser().get400x400ProfileImageURL(), responseList.get().get(0).getUser().getProfileImageUrl());
        assertEquals(testUrl, responseList.get().get(0).getPostUrl());

    }

    @Test
    public void getUserTimelineTestSuccess() {

        Optional<List<Status>> responseList = Optional.empty();

        try {

            when(mockFactory.getUserTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getUserTimeline();

        } catch (Exception e) {

            fail("Test failed due to Twitter Exception");

        }

        assertEquals(exampleStatus.getText(), responseList.get().get(0).getMessage());
        assertEquals(exampleStatus.getCreatedAt(), responseList.get().get(0).getCreatedAt());
        assertEquals(exampleStatus.getUser().getName(), responseList.get().get(0).getUser().getName());
        assertEquals(exampleStatus.getUser().getScreenName(), responseList.get().get(0).getUser().getTwHandle());
        assertEquals(exampleStatus.getUser().get400x400ProfileImageURL(), responseList.get().get(0).getUser().getProfileImageUrl());
        assertEquals(testUrl, responseList.get().get(0).getPostUrl());

    }

    @Test
    public void getCachedTimelineTest() {

        List<twitter4j.Status> testList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            testList.add(new Twitter4jStatusImpl());
        }

        HashMap<String, List<twitter4j.Status>> testHashMap = new HashMap<>();
        Optional<List<Status>> testResponse = null;

        testHashMap.put("home", testList);

        try {

            when(cacheUp.getTimelineCache()).thenReturn(testHashMap);
            testResponse = twSingleton.getHomeTimeline();

        } catch(TwitterServiceException e) {

            fail("TwitterService unit test failed due to TwitterServiceException.");

        }

        assertTrue(testResponse.get().size() == 3);
    }

    @Test
    public void getFactoryTest() {

        assertEquals(mockFactory, twSingleton.getTwitterFactory());

    }

    @Test
    public void setCacheUpTest() {

        CacheUp testCacheUp = new CacheUp();
        twSingleton.setCacheUp(testCacheUp);

        assertEquals(testCacheUp.getTimelineCache().get("home"), twSingleton.getCacheUp().getTimelineCache().get("home"));
    }
}
