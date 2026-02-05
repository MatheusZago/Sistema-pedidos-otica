package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.service.LenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/lentes")
public class LenteController {

    private final LenteService service;

    public LenteController(LenteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Lente> save(@RequestBody LenteRegisterDto dto){
        Lente saved = service.save(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }
}
