package com.khoros.twitterapp;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class HomeFeedResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new HomeFeedResource()).build();

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetPersion() {

    }
}
