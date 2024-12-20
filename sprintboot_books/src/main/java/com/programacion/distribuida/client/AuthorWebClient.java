package com.programacion.distribuida.client;

import com.programacion.distribuida.dto.AuthorDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthorWebClient {

    private WebClient.Builder webClient;

    public AuthorWebClient(@LoadBalanced WebClient.Builder webClientB) {
        this.webClient = webClientB;
    }

    public AuthorDto findAuthorById(Integer id) {
        return webClient.build()
                .get()
                .uri("http://springboot-authors/authors/{id}", id) // Nombre del servicio registrado en Consul
                .retrieve()
                .bodyToMono(AuthorDto.class)
                .block();
    }
}
