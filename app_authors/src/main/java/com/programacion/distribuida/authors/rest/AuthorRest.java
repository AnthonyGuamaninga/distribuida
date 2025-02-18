package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Tag(name = "Authors", description = "Gestiona información sobre authors")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
//@Transactional
public class AuthorRest {

    @Inject
    private AuthorRepository repository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer port;

    AtomicInteger counter = new AtomicInteger(1);

    @GET
    @Operation(
            summary = "Obtener un autor por su id (identidicador)",
            description = "Se realiza la busqueda de un Author por su id"
    )
    @APIResponse(
            responseCode = "200",
            description = "Author obtenido correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))
    )
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) throws UnknownHostException {

        int value = counter.getAndIncrement();
        if(value%5 != 0) {
            String msg = String.format("Intento %d ==> error", value);
            System.out.println("*********** "+msg);
            throw new RuntimeException(msg);
        }

        System.out.printf("%s: Server %d\n", LocalDateTime.now(), port);

        var obj = repository.findById(id);

        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        String txt = String.format("[%s:%d]-%s", ipAddress,port, obj.getFirstName());

        var ret = new Author();
        ret.setId(obj.getId());
        ret.setFirstName(txt);
        ret.setLastName(obj.getLastName());

        return Response.ok(ret).build();
    }

    @GET
    @Operation(
            summary = "Obtener todos los autores",
            description = "Devuelve una lista de autores disponibles"
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de autores obtenida correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))
    )
    public List<Author> findAll(){
        return repository.findAll()
                .list();
    }

    @POST
    @Operation(
            summary = "Registrar un autor",
            description = "Se envia un json con los atributos que posee author"
    )
    @APIResponse(
            responseCode = "200",
            description = "Registro de autor realizado correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))
    )
    public Response create(Author author){
        repository.persist(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Operation(
            summary = "Actulizar autor",
            description = "Se envía un objeto es formato json para actualizar a un author ya existente a través de su id"
    )
    @APIResponse(
            responseCode = "200",
            description = "Actulización de author satisfactoria",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))
    )
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author author){
        var obj = repository.update(id, author);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj).build();
    }

    @DELETE
    @Operation(
            summary = "Elimina un author",
            description = "Se envia por el path el id del author que se desea eliminar"
    )
    @APIResponse(
            responseCode = "200",
            description = "Author eliminado de forma satisfactoria",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class))
    )
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id){
        var obj = repository.deleteById(id);
        if(!obj){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }


}
