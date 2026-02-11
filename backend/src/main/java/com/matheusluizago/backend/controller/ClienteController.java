package com.matheusluizago.backend.controller;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.dto.errorDto.ErrorResponseDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
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

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Endpoint para gerenciamento de clientes.")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar novo cliente.",
            description = "Salva um novo cliente no banco de dados e retorna o recurso criado. "
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
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRegisterDto clienteDto){

        Cliente saved = service.save(clienteDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @Operation(
            summary = "Buscar clientes.",
            description = "Busca uma lista de todos os clientes de acordo com parametros. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = ClienteResponseDto.class))),
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> search(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "telefone", required = false) String telefone,
            @RequestParam(value = "email", required = false) String email
    ){

        List<ClienteResponseDto> result = service.search(id, nome, telefone, email);

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Atualizar cliente.",
            description = "Atualiza um cliente já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso!",
                    content = @Content(
                            schema = @Schema(implementation = ClienteResponseDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos inválidos. ",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Integer id,
            @RequestBody ClienteUpdateDto dto
    ){

        return ResponseEntity.ok(service.update(id, dto));

    }

    @Operation(
            summary = "Deletar cliente.",
            description = "Deletar um cliente já existente no banco de dados. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente deletado com sucesso!"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado.",
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
