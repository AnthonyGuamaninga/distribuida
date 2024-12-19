package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Book;
import com.programacion.distribuida.service.BookService;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

public class RestBook {

    private static ContainerLifecycle lifecycle = null;
    private static Jsonb jsonb = JsonbBuilder.create();
    static {
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true);
        jsonb = JsonbBuilder.create(config);
    }

    private static void findById(ServerRequest req, ServerResponse res) {
        BookService service = CDI.current().select(BookService.class).get();
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        res.send(jsonb.toJson(service.getById(id)));
    }
    private static void findAll(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        res.send(jsonb.toJson(service.getAll()));
    }
    private static void insert(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        var book = jsonb.fromJson(req.content().as(String.class), Book.class);
        res.send(jsonb.toJson(service.save(book)));
    }
    private static void update(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        var book = jsonb.fromJson(req.content().as(String.class), Book.class);
        res.send(jsonb.toJson(service.update(id, book)));
    }
    private static void delete(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        res.send(jsonb.toJson(service.delete(id)));
    }

    public static void main(String[] args) {

        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        WebServer.builder()
                .routing( it -> it
                        .get("/books/{id}", RestBook::findById)
                        .get("/books", RestBook::findAll)
                        .post("/books", RestBook::insert)
                        .put("/books/{id}", RestBook::update)
                        .delete("/books/{id}", RestBook::delete)
                )
                .port(8080)
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Apagando la aplicación...");
            shutdown();
        }));

    }

    public static void shutdown()  {
        lifecycle.stopApplication(null);
    }

}
