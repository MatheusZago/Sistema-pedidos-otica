package com.matheusluizago.backend.dto.clienteDto;

public record ClienteResponseDto (
        Integer id,
        String nome,
        String telefone,
        String email,
        String foto
) { }
