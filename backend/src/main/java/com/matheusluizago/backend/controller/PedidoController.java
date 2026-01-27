package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final Logger log = LoggerFactory.getLogger(PedidoController.class);


    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido){
        log.info("Registrando novo laboratório: {}", pedido.getArmacao());

        Pedido saved = service.save(pedido);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Pedido>> getById(@PathVariable("id")int id){
        Optional<Pedido> pedido = service.getById(id);

        return ResponseEntity.ok(pedido);
    }
}
