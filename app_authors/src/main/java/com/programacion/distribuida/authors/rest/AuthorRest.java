package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.List;

@Path("/authors")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
@Transactional
public class AuthorRest {

    @Inject
    private AuthorRepository repository;

    @Inject
    @ConfigProperty(name ="quarkus.http.port")
    Integer port;

    @GET
    public List<Author> findAll(){
        return repository.findAll()
                .list();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id){
        System.out.printf("%s: servidor %d\n", LocalDateTime.now(), port);

        var obj = repository.findById(id);

        String txt = String.format("[%d]-%s", port, obj.getFirstName());

        var ret = new Author();
        ret.setFirstName(obj.getFirstName());
        ret.setLastName(obj.getLastName());
        ret.setId(obj.getId());

        obj.setLastName(txt);

        return Response.ok(obj).build();
    }

    @POST
    public Response create(Author author){
        repository.persist(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author author){
        var obj = repository.update(id, author);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id){
        var obj = repository.deleteById(id);
        if(!obj){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }


}
