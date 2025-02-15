package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dto.AuthorDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@LoadBalancerClient(name = "app-load-balancer")
public class AuthorRestTemplate {

    private final RestTemplate restTemplate;

    AuthorRestTemplate(@LoadBalanced RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Retry(name = "authorsServiceRetry", fallbackMethod = "findByIdFallback")
    public AuthorDto findById(Integer id) {
        return restTemplate.getForObject("http://app-authors/authors/{id}", AuthorDto.class, id);
    }

    public AuthorDto findByIdFallback(Integer id, Throwable t) {
        var dto = new AuthorDto();
        dto.setId(-1);
        dto.setFirstName("noname");
        dto.setLastName("");
        return dto;
    }

}
