package com.programacion.distribuida.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programacion.distribuida.db.Book;
import com.programacion.distribuida.service.BookService;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

public class PrincipalRestBook {

    private static ContainerLifecycle lifecycle;
//    private static
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static void getAllBooks(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        res.send(gson.toJson(service.getAll()));
    }
    static void getBookById(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        res.send(gson.toJson(service.getById(id)));
    }
    static void addBook(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        Book book = gson.fromJson(req.content().as(String.class), Book.class);
        res.send(gson.toJson(service.insertBook(book)));
    }
    static void updateBook(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        Book book = gson.fromJson(req.content().as(String.class), Book.class);
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        res.send(gson.toJson(service.putBook(id, book)));
    }
    static void deleteBook(ServerRequest req, ServerResponse res){
        BookService service = CDI.current().select(BookService.class).get();
        var id = Integer.parseInt(req.path().pathParameters().get("id"));
        res.send(gson.toJson(service.deleteBook(id)));
    }



    public static void main(String[] args) {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        WebServer.builder()
                .routing( it -> it
                        .get("/books", PrincipalRestBook::getAllBooks)
                        .get("/books/{id}", PrincipalRestBook::getBookById)
                        .post("/books", PrincipalRestBook::addBook)
                        .put("/books/{id}", PrincipalRestBook::updateBook)
                        .delete("/books/{id}", PrincipalRestBook::deleteBook)
                )
                .port(8080)
                .build()
                .start();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Apagando la aplicación...");
            shutdown();
        }));
    }

    public static void shutdown() {
        lifecycle.stopApplication(null);
    }

}
