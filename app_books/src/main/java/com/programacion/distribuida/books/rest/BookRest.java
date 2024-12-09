package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
@Transactional
public class BookRest {

    @Inject
    BookRepository repository;

    @GET
    public List<Book> findAll(){
        return repository.findAll()
                .list();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id){
        var obj = repository.findByIdOptional(id);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(obj.get()).build();
    }

    @POST
    public Response create(Book book){
        repository.persist(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Book book){
        var obj = repository.update(id, book);

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
