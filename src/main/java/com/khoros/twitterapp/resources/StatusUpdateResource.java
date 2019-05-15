package com.khoros.twitterapp.resources;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    private int maxTweetLength;
    private Configuration conf;
    private Twitter factory = new TwitterFactory(conf).getInstance();

    public StatusUpdateResource(Configuration conf, int maxTweetLength) {
        this.conf = conf;
        this.maxTweetLength = maxTweetLength;
    }

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        String statusText = tweetText.trim();
        try {
            if (statusText.length() == 0) {
                return Response.status(403).entity("No tweet text entered.").build();
            } else if (statusText.length() > maxTweetLength) {
                return Response.status(403).entity("Tweet text surpassed 280 characters.").build();
            } else {
                Status newStatus = factory.updateStatus(statusText);
                return Response.status(200).entity(newStatus).build();
            }
        } catch (TwitterException tweetException) {
            return Response.status(tweetException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }

    // method for testing using mock Twitter object
    public void setFactory(Twitter factory) {
        this.factory = factory;
    }
}
