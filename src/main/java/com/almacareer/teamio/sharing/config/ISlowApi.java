package com.almacareer.teamio.sharing.config;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/slow")
public interface ISlowApi {

    @GET
    @Path("/{seconds}")
    String slow(@PathParam("seconds") int seconds);
}
