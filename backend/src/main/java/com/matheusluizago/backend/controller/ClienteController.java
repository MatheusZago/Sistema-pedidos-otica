package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final Logger log = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        log.info("Registrando novo cliente: {}", cliente.getNome());

        Cliente saved = service.save(cliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Cliente>> getById(@PathVariable("id")int id){
        Optional<Cliente> cliente = service.getById(id);

         return ResponseEntity.ok(cliente);
    }

}
