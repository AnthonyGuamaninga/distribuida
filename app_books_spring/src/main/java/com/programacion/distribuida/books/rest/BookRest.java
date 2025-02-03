package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.dto.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
@Transactional
public class BookRest {
    @Autowired
    BookRepository repository;

    //@Autowired
    //@RestClient
    //AuthorRestClient client;

    @Autowired
    private RestClient.Builder restClientBuilder;

    @GetMapping
    public List<BookDto> findAll() {

        var restClient = restClientBuilder.baseUrl("http://app-authors-spring")
                .build();

        var adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        AuthorRestClient service = factory.createClient(AuthorRestClient.class);

        //version-4-->MP Client Automatica
        return repository.findAll()
                .stream()
                .map(book->{
                    System.out.println("Buscando author con id= " + book.getAuthorId());

                    var author = service.findById(book.getAuthorId());

//                    var author = restClientBuilder.build()
//                            .get()
//                            .uri("http://app-authors/authors/{id}", book.getAuthorId())
//                            .retrieve()
//                            .body(AuthorDto.class);

                    var dto = new BookDto( );

                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    dto.setAuthorName(author.getFirstName() + " " + author.getLastName());

                    return dto;
                })
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable("id") Integer id) {
        var obj = repository.findById(id);

        if(obj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(obj.get());
    }
}
