package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;

import twitter4j.Status;

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


    public TwitterService twitterService = TwitterService.getInstance();

    @Path("/tweet")
    @Consumes("application/x-www-form-urlencoded")
    @POST
    public Response post(@FormParam("message") String tweetText) {

        String statusText = tweetText.trim();

        try {

            Status twitterStatus = twitterService.updateStatus(statusText);
            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterStatus).build();

        } catch (TwitterServiceException twServiceException) {

            if (twServiceException.getCause() == null) {

                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(twServiceException.getMessage()).build();

            } else {

                return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(
                        twServiceException.getCause().getMessage()
                ).build();

            }
        }

    }

    @Path("/timeline")
    @GET
    public Response get() {

        try {

            List<Status> twitterFeed = twitterService.getHomeTimeline();
            return Response.status(HttpURLConnection.HTTP_OK).entity(twitterFeed).build();

        } catch (TwitterServiceException twServiceException) {

            return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(
                    twServiceException.getCause().getMessage()
            ).build();

        }

    }
}
