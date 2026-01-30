package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.ClienteResponseDto;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;
    private final Logger log = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController(ClienteService service, ClienteMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody ClienteRegisterDto clienteDto){
        log.info("Registrando novo cliente: {}", clienteDto.nome());

        Cliente saved = service.save(clienteDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> search(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email
    ){

        List<ClienteResponseDto> result = service.searchByExample(id, nome, telefone, email);

        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Integer id,
            @RequestBody ClienteRegisterDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }


}
