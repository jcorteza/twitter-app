package com.khoros.twitterapp;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.Optional;

@Path("/api/1.0/twitter/timeline")
@Produces(MediaType.TEXT_PLAIN)
public class HomeFeedResource {

    @GET
    public Response get() {
        return Response.status();
    }
}
