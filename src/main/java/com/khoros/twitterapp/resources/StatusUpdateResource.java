package com.khoros.twitterapp.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    private Twitter factory = new TwitterFactory().getSingleton();

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        String statusText = tweetText.trim();
        try {
            if (statusText.length() == 0) {
                return Response.status(403).entity("No tweet text entered.").build();
            } else if (statusText.length() > 280) {
                return Response.status(403).entity("Tweet text surpassed 280 characters.").build();
            } else {
                Status newStatus = factory.updateStatus(statusText);
                return Response.status(200).entity(newStatus).build();
            }
        } catch (TwitterException tweetException) {
            return Response.status(tweetException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }

    public void setFactory(Twitter factory) {
        this.factory = factory;
    }
}
