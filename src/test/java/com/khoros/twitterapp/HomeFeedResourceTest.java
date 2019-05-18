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

    private TwitterService mockTWService;
    private TwitterResponse twResponse;
    private HomeFeedResource feedResource;

    @Before
    public void setup() {

        feedResource = new HomeFeedResource();
        mockTWService = mock(TwitterService.class);
        twResponse = mockTWService.getHomeTimeline();

    }

    @After
    public void resetMock() {
        reset(mockTWService);
    }

    @Test
    public void resourceGetSuccess() {

        Assert.assertEquals(HttpURLConnection.HTTP_OK, feedResource.get().getStatus());
        Assert.assertEquals(twResponse, feedResource.get().getEntity());

    }

    @Test
    public void resourceGetException() throws TwitterException {

        when(twResponse).thenThrow(
                new TwitterException("Testing TwitterException.", new Exception(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, feedResource.get().getStatus());
        Assert.assertEquals(TwitterApp.GENERAL_ERR_MSG, feedResource.get().getEntity());
    }
}
