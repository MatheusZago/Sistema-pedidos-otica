package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.service.LaboratorioService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService service;
    private final Logger log = LoggerFactory.getLogger(LaboratorioController.class);


    public LaboratorioController(LaboratorioService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Laboratorio> save(@RequestBody Laboratorio laboratorio){
        log.info("Registrando novo laboratório: {}", laboratorio.getNome());

        Laboratorio saved = service.save(laboratorio);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Laboratorio>> getById(@PathVariable("id")int id){
        Optional<Laboratorio> laboratorio = service.getById(id);

        return ResponseEntity.ok(laboratorio);
    }


}
