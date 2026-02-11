package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.errorDto.ErrorResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.service.LenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Tag(name = "Lentes", description = "Endpoint para gerenciamento de Lentes.")
@RestController
@RequestMapping("/lentes")
public class LenteController {

    private final LenteService service;

    public LenteController(LenteService service){
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar novo lente.",
            description = "Salva uma nova lente no banco de dados e retorna o recurso criado. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Lente criado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = Lente.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Lente já existente.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
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

    @Operation(
            summary = "Buscar lentes.",
            description = "Busca uma lista de todas as lentes de acordo com parametros. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = LenteResponseDto.class))),
    })
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

    @Operation(
            summary = "Atualizar lente.",
            description = "Atualiza um lente já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lente atualizado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = LenteResponseDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lente não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PutMapping("{id}")
    public ResponseEntity<LenteResponseDto> update(
            @PathVariable Integer id,
            @RequestBody LenteUpdateDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }

    @Operation(
            summary = "Deletar lente.",
            description = "Deletar uma lente já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lente deletada com sucesso!"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Lente não encontrada.",
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
