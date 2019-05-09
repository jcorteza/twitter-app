package com.khoros.twitterapp;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
public class StatusUpdateResource {
    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        Twitter factory = new TwitterFactory().getSingleton();
        String statusText = tweetText.trim();
        try {
            if (statusText.length() > 0 && statusText.length() <= 280) {
                Status newStatus = factory.updateStatus(statusText);
                System.out.println("Status was successfully updated to \"" + statusText + "\"");
                return Response.status(200).build();
            } else {
                throw new LengthException(statusText.length());
            }
        } catch (LengthException lengthException) {
            System.out.println(lengthException.getExceptionMessage());
            System.out.println(lengthException.getCauseMessage());
            return Response.status(403).build();
        } catch (TwitterException tweetException) {
            tweetException.printStackTrace();
            System.out.println("errorCode: " + tweetException.getErrorCode());
            System.out.println(tweetException.getErrorMessage());
            return Response.status(tweetException.getStatusCode()).build();
        }
    }

}
