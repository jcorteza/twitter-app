package com.khoros.twitterapp;

import com.khoros.twitterapp.TweetText;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
// import java.util.Optional;

@Path("/api/1.0/twitter/tweet?message={tweetText}")
@Consumes("application/x-www-form-urlencoded")
public class StatusUpdateResource {
    @POST
    public Response postStatus(@FormParam("tweetText") String tweetText) {
        return Response.status();
    }

}
