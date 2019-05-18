package com.khoros.twitterapp;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.resources.StatusUpdateResource;
import com.khoros.twitterapp.services.TwitterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.lang.Exception;
import java.net.HttpURLConnection;
import org.apache.commons.lang3.StringUtils;

public class StatusUpdateResourceTest {

    private StatusUpdateResource statusResource;
    private TwitterService mockTWService;
    private String exampleText;
    private String exceptionEntity;
    private Status statusEntity;

    @Before
    public void setup() {
        mockTWService = mock(TwitterService.class);
        statusResource = new StatusUpdateResource();
        statusResource.setTwitterService(mockTWService);
    }

    @After
    public void resetMock() {
        reset(mockTWService);
    }

    @Test
    public void statusUpdateTest() {
        exampleText = "Tweet";

        Assert.assertEquals(HttpURLConnection.HTTP_OK, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(statusEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusLengthZeroTest() {
        exampleText = "";
        exceptionEntity = StatusUpdateResource.NO_TWEET_TEXT_MSG;

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(exceptionEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusLengthLongTest() {
        exampleText = StringUtils.repeat("a", TwitterApp.MAX_TWEET_LENGTH + 1);
        exceptionEntity = StatusUpdateResource.TWEET_TOO_LONG_MSG;

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(exceptionEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusTwitterExceptionTest() throws TwitterException {
        exampleText = "Exception.";

        when(mockTWService.updateStatus(exampleText)).thenThrow(
                new TwitterException("Testing TwitterException.", new Exception(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(TwitterApp.GENERAL_ERR_MSG, statusResource.postStatus(exampleText).getEntity());
    }
}
