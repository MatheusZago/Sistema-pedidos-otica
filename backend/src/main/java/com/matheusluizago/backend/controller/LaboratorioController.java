package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.service.LaboratorioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService service;
    private final Logger log = LoggerFactory.getLogger(LaboratorioController.class);


    public LaboratorioController(LaboratorioService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Laboratorio> save(@RequestBody LaboratorioRegisterDto labDto){
        log.info("Registrando novo laboratório: {}", labDto.nome());

        Laboratorio saved = service.save(labDto);

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
