package com.programacion.distribuida.books.clients;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/authors")
@Produces("application/json")
@Consumes("application/json")
public interface AuthorRestClient {

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id);


}
