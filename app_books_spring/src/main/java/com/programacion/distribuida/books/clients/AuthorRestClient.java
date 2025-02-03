package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dto.AuthorDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/authors")
public interface AuthorRestClient {
    @GetExchange("/{id}")
    AuthorDto findById(@PathVariable("id") Integer id);
}
