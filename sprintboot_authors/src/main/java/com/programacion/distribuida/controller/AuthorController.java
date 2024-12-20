package com.programacion.distribuida.controller;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.rest.AuthorRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consul")
public class AuthorController {

    @Value("${spring.cloud.consul.host:127.0.0.1}")
    String consulIp;

    @Value("${spring.cloud.consul.port:8500}")
    Integer consulPort;

    private final AuthorRest authorRest;

    @Autowired
    public AuthorController(AuthorRest authorRest) {
        this.authorRest = authorRest;
    }

    // Endpoint para obtener un autor por ID
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Integer id) {
        return authorRest.findById(id).getBody();
    }

    // Endpoint para verificar conexión con Consul
    @GetMapping("/consul-status")
    public String getConsulStatus() {
        return String.format("Consul running on %s:%d", consulIp, consulPort);
    }


}
