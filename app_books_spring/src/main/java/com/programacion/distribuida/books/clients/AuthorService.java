package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dto.AuthorDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class AuthorService {

    @Autowired
    private RestClient.Builder restClientBuilder;
    private final AuthorRestClient authorRestClient;
//    private final Retry retry;

    public AuthorService(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder.baseUrl("http://app-authors")
                .build();
//        this.retry = retryRegistry.retry("authorsServiceRetry");
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        this.authorRestClient = factory.createClient(AuthorRestClient.class);
    }

    @Retry(name = "authorsServiceRetry", fallbackMethod = "findByIdFallback")
    public AuthorDto findById(Integer id) {
        return authorRestClient.findById(id);
    }

    public AuthorDto findByIdFallback(Integer id, Throwable t) {
        var dto = new AuthorDto();
        dto.setId(-1);
        dto.setFirstName("noname");
        dto.setLastName("");
        return dto;
    }
}
