package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestTemplate;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dto.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
@Transactional
public class BookRest {
    @Autowired
    BookRepository repository;

//    @Autowired
//    private AuthorService service;

    private AuthorRestTemplate service;

    public BookRest(AuthorRestTemplate authorRestTemplate) {
        this.service = authorRestTemplate;
    }

    @GetMapping
    public List<BookDto> findAll() {
        System.out.println("---NUEVO----");

        //version-4-->MP Client Automatica
        return repository.findAll()
                .stream()
                .map(book->{
                    System.out.println("Buscando author con id= " + book.getAuthorId());
                    var author = service.findById(book.getAuthorId());

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
    public ResponseEntity<BookDto> findById(@PathVariable("id") Integer id) {
        var obj = repository.findById(id);

        if (obj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var book = obj.get();
        var author = service.findById(book.getAuthorId());

        var dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setAuthorName(author.getFirstName() + " " + author.getLastName());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Book book) {
        repository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("New book inserted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book book) {
        var actualizable = repository.findById(id).get();
        if (actualizable == null) {
            return ResponseEntity.notFound().build();
        }
        actualizable.setPrice(book.getPrice());
        actualizable.setTitle(book.getTitle());
        actualizable.setIsbn(book.getIsbn());
        actualizable.setAuthorId(book.getAuthorId());

        repository.save(actualizable);
        return ResponseEntity.ok(actualizable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        var book = repository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(book.get());
        return ResponseEntity.ok("Book "+id+" deleted");
    }

}
