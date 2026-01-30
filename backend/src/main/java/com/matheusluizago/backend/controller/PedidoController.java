package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.PedidoResponseDto;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final Logger log = LoggerFactory.getLogger(PedidoController.class);


    public PedidoController(PedidoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody PedidoRegisterDto dto){

        Pedido saved = service.save(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> searchByExample(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "clienteId", required = false) Integer clienteId,
            @RequestParam(value = "clienteNome", required = false) String clienteNome,
            @RequestParam(value = "clienteEmail", required = false) String clienteEmail,
            @RequestParam(value = "clienteTelefone", required = false) String clienteTelefone,
            @RequestParam(value = "laboratorioId", required = false) Integer labId,
            @RequestParam(value = "laboratorioNome", required = false) String labNome,
            @RequestParam(value = "laboratorioEndereco", required = false) String labEndereco,
            @RequestParam(value = "custo", required = false) BigDecimal custo,
            @RequestParam(value = "armacao", required = false) String armacao,
            @RequestParam(value = "od", required = false) BigDecimal od,
            @RequestParam(value = "oe", required = false) BigDecimal oe,
            @RequestParam(value = "ad", required = false) BigDecimal ad,
            @RequestParam(value = "dnp", required = false) BigDecimal dnp,
            @RequestParam(value = "tratamento", required = false) String tratamento,
            @RequestParam(value = "tipoLente", required = false) String tipoLente

    ) {
        List<PedidoResponseDto> list = service.search(id, clienteId, clienteNome, clienteEmail, clienteTelefone,
                labId, labNome, labEndereco,
                custo, armacao, od, oe, ad, dnp,
                tratamento, tipoLente);

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoResponseDto> update(
            @PathVariable Integer id,
            @RequestBody PedidoRegisterDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }


}
