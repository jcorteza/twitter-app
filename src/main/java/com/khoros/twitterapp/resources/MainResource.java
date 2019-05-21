package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import com.khoros.twitterapp.services.TwitterService;

import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.List;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {

    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterApp.MAX_TWEET_LENGTH + " characters.";
    public TwitterService twitterService = TwitterService.getInstance();

    @Path("/tweet")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response post(@FormParam("message") String tweetText) {

        String statusText = tweetText.trim();

        if (statusText.length() == 0) {

            return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(MainResource.NO_TWEET_TEXT_MSG).build();

        } else if (statusText.length() > TwitterApp.MAX_TWEET_LENGTH) {

            return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(MainResource.TWEET_TOO_LONG_MSG).build();

        } else {

            try {

                Status twitterStatus = twitterService.updateStatus(statusText);
                return Response.status(HttpURLConnection.HTTP_OK).entity(twitterStatus).build();

            } catch (TwitterException twitterException) {

                return Response.status(twitterException.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

            }

        }

    }

    @Path("/timeline")
    @GET
    public Response get() {

        try {

            List<Status> twitterFeed = TwitterService.getInstance().getHomeTimeline();
            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterFeed).build();

        } catch (TwitterException twitterException) {

            return Response.status(twitterException.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

        }

    }
}
