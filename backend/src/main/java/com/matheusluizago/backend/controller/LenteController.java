package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.service.LenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lentes")
public class LenteController {

    private final LenteService service;

    public LenteController(LenteService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Lente> save(@RequestBody @Valid LenteRegisterDto dto){
        Lente saved = service.save(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<LenteResponseDto>> search(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "tipoLente", required = false) String tipoLente,
            @RequestParam(value = "custo", required = false) BigDecimal custo,
            @RequestParam(value = "tratamento", required = false) String tratamento,
            @RequestParam(value = "indice", required = false) String indice,
            @RequestParam(value = "valorVenda", required = false) BigDecimal valorVenda
    ){

        List<LenteResponseDto> list = service.search(id, tipoLente, custo, tratamento, indice, valorVenda);

        return ResponseEntity.ok(list);
    }
}
