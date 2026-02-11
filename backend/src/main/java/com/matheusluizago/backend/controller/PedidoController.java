package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.errorDto.ErrorResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Cadastrar novo pedido.",
            description = "Salva um novo pedido no banco de dados e retorna o recurso criado. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Pedido já existente.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
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

    @Operation(
            summary = "Buscar pedidos.",
            description = "Busca uma lista de todos os pedidos de acordo com parametros. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = PedidoResponseDto.class))),
    })
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
            @RequestParam(value = "laboratorioCnpj", required = false) String labCnpj,
            @RequestParam(value = "lenteId", required = false) Integer lenteId,
            @RequestParam(value = "lenteCusto", required = false) BigDecimal lenteCusto,
            @RequestParam(value = "lenteTratamento", required = false) String lenteTratamento,
            @RequestParam(value = "lenteIndice", required = false) String lenteIndice,
            @RequestParam(value = "tipoLente", required = false) String tipoLente,
            @RequestParam(value = "valorVenda", required = false) BigDecimal valorVenda,
            @RequestParam(value = "armacao", required = false) String armacao,
            @RequestParam(value = "od", required = false) BigDecimal od,
            @RequestParam(value = "oe", required = false) BigDecimal oe,
            @RequestParam(value = "ad", required = false) BigDecimal ad,
            @RequestParam(value = "dnp", required = false) BigDecimal dnp


    ) {
        List<PedidoResponseDto> list = service.search(id, clienteId, clienteNome, clienteEmail, clienteTelefone,
                labId, labNome, labEndereco, labCnpj,
                lenteId, lenteCusto, lenteTratamento,
                lenteIndice, tipoLente, valorVenda,
                armacao, od, oe, ad, dnp
                );

        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Atualizar pedido.",
            description = "Atualiza um pedido já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido atualizado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = PedidoResponseDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PutMapping("{id}")
    public ResponseEntity<PedidoResponseDto> update(
            @PathVariable Integer id,
            @RequestBody PedidoUpdateDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }

    @Operation(
            summary = "Deletar pedido.",
            description = "Deletar um pedido já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido deletado com sucesso!"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
