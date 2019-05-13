package com.khoros.twitterapp;

import org.junit.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;

public class HomeFeedResourceTest {

    @InjectMocks
    private HomeFeedResource feedResource;

    @Mock
    private Twitter mockFactory;

    @Before
    public void setup() {
        feedResource = new HomeFeedResource();
        mockFactory = mock(Twitter.class);

        try {
            when(mockFactory.getHomeTimeline()).thenReturn("Twitter API call successful!");
        } catch(TwitterException e) {
            Assert.fail("Test failed due to TwitterException: " + e.getMessage());
        }
    }

    @Test
    public void resourceGetSuccess() {
        Assert.assertEquals(Response.status(200).entity("Twitter API call successful!").build(), feedResource.get());
    }
}
