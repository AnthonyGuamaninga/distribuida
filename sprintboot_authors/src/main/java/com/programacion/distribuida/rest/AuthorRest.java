package com.programacion.distribuida.rest;


import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repo.AuthorRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/authors")
@Transactional
public class AuthorRest {

    @Autowired
    private AuthorRepo repository;

    @Value("${server.port}")
    Integer port;

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Integer id){
        System.out.printf("%s: Server %d\n", LocalDateTime.now(), port);

        var author = repository.findById(id);

        if(author.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author.get());
    }

    @GetMapping
    public List<Author> findAll(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> create(Author author){
        repository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author) {
        var optionalAuthor = repository.findById(id);
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        author.setId(id); // Ensure the ID is maintained
        repository.save(author);

        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        var exists = repository.existsById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
