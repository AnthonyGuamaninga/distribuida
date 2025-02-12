package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dto.AuthorDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@Service
@HttpExchange(url = "/authors")
public interface AuthorRestClient {

//    @Retry(name = "authorsServiceRetry", fallbackMethod = "findByIdFallback")
    @GetExchange("/{id}")
    AuthorDto findById(@PathVariable("id") Integer id);

//    default AuthorDto findByIdFallback(Integer id, Throwable t) {
//        System.out.println("Fallback ejecutado debido a: " + t.getMessage());
//
//        var dto = new AuthorDto();
//        dto.setId(-1);
//        dto.setFirstName("Autor no disponible");
//        dto.setLastName("");
//
//        return dto;
//    }
}
