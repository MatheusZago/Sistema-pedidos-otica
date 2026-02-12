package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.errorDto.ErrorResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.service.LaboratorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Laboratórios", description = "Endpoint para gerenciamento de laboratórios.")
@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService service;
    private final Logger log = LoggerFactory.getLogger(LaboratorioController.class);


    public LaboratorioController(LaboratorioService service){
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar novo laboratório.",
            description = "Salva um novo laboratório no banco de dados e retorna o recurso criado. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cliente já existente.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PostMapping
    public ResponseEntity<LaboratorioResponseDto> save(@RequestBody LaboratorioRegisterDto labDto){
        log.info("Registrando novo laboratório: {}", labDto.nome());

        LaboratorioResponseDto saved = service.save(labDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @Operation(
            summary = "Buscar laboratórios.",
            description = "Busca uma lista de todos os laboratório de acordo com parametros. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = LaboratorioResponseDto.class))),
    })
    @GetMapping
    public ResponseEntity<List<LaboratorioResponseDto>> search(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "endereco", required = false) String endereco,
            @RequestParam(value = "cnpj", required = false) String cnpj
    ){

        List<LaboratorioResponseDto> list = service.search(id, nome, endereco, cnpj);

        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Atualizar laboratório.",
            description = "Atualiza um laboratório já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laboratório atualizado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = LaboratorioResponseDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laboratório não encontrado.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PutMapping("{id}")
    public ResponseEntity<LaboratorioResponseDto> update(
            @PathVariable Integer id,
            @RequestBody LaboratorioUpdateDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }

    @Operation(
            summary = "Deletar laboratório.",
            description = "Deletar um laboratório já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Laboratório deletado com sucesso!"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Laboratório não encontrado.",
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
