package com.khoros.twitterapp;

import org.junit.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;
import java.util.List;

public class HomeFeedResourceTest {

    @InjectMocks
    private HomeFeedResource feedResource;

    @Mock
    private Twitter mockFactory;

    List<Status> mockStatus;
    private Response successResponse;

    @Before
    public void setup() {
        feedResource = new HomeFeedResource();
        mockFactory = mock(Twitter.class);

        try {
            mockStatus = mockFactory.getHomeTimeline();
        } catch(TwitterException e) {
            Assert.fail("Test failed due to TwitterException: " + e.getMessage());
        }

        successResponse = Response.status(200).entity(mockStatus).build();
    }

    @Test
    public void resourceGetSuccess() {
        Assert.assertEquals(successResponse, feedResource.get());
    }
}
