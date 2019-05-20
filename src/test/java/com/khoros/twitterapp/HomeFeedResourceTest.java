package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.HomeFeedResource;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.services.TwitterService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import twitter4j.*;

import java.lang.Exception;
import java.net.HttpURLConnection;

public class HomeFeedResourceTest {

    private TwitterService twSingleton;
    private HomeFeedResource feedResource;

    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        twSingleton.setMockTWFactory(mock(Twitter.class));
        feedResource = new HomeFeedResource();

    }

    @After
    public void resetMock() {

        twSingleton.resetTWFactory();

    }

    @Test
    public void resourceGetSuccess() {

        TwitterResponse twResponse = new ResponseImplTest<Status>();
        when(twSingleton.getHomeTimeline()).thenReturn(twResponse);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, feedResource.get().getStatus());
        Assert.assertEquals(twResponse, feedResource.get().getEntity());

    }

    @Test
    public void resourceGetException() throws TwitterException {

        when(twSingleton.getHomeTimeline()).thenThrow(
                new TwitterException("Testing TwitterException.", new Exception(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, feedResource.get().getStatus());
        Assert.assertEquals(TwitterApp.GENERAL_ERR_MSG, feedResource.get().getEntity());
    }
}
