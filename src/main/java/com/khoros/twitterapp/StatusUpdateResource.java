package com.khoros.twitterapp;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {
    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        Twitter factory = new TwitterFactory().getSingleton();
        String statusText = tweetText.trim();
        try {
            if (statusText.length() > 0 && statusText.length() <= 280) {
                Status newStatus = factory.updateStatus(statusText);
                return Response.status(200).entity(newStatus).type("application/json").build();
            } else {
                throw new LengthException(statusText.length());
            }
        } catch (LengthException lengthException) {
            return Response.status(403).entity(lengthException.getCauseMessage()).build();
        } catch (TwitterException tweetException) {
            return Response.status(tweetException.getStatusCode()).entity(tweetException.getErrorMessage()).build();
        }
    }
}
