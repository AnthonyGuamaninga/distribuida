package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dto.AuthorDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/authors")
public interface AuthorRestClient {

    @GetExchange("/{id}")
    @CircuitBreaker(name = "authorService", fallbackMethod = "findByIdFallback")
    AuthorDto findById(@PathVariable("id") Integer id);

    default AuthorDto findByIdFallback(Integer id, Exception ex) {
        System.out.println("Ejecutando fallback para findById del autor...");

        // Crear un AuthorDto con valores predeterminados
        var dto = new AuthorDto();
        dto.setId(-1);
        dto.setFirstName("Autor no disponible");
        dto.setLastName("");

        return dto;
    }
}
