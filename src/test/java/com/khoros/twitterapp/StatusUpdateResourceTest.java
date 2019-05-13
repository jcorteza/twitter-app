package com.khoros.twitterapp;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;

public class StatusUpdateResourceTest {

    @InjectMocks
    private StatusUpdateResource  statusResource;

    @Mock
    private Twitter mockFactory;

    private String exampleText;
    private Status entity;
    private Response exampleResponse;

    @Before
    public void setup() {
        statusResource = new StatusUpdateResource();
        mockFactory = mock(Twitter.class);
        exampleText = "Hello Twitter!";

        try {
            entity = mockFactory.updateStatus(exampleText);
        } catch (TwitterException e) {
            Assert.fail("Test failed due to Twitter Exception: " + e.getMessage());
        }
    }

    @Test
    public void statusUpdateTest() {
        exampleResponse = Response.status(200).entity(entity).build();
        Assert.assertEquals(exampleResponse, statusResource.postStatus(exampleText));
    }
}
