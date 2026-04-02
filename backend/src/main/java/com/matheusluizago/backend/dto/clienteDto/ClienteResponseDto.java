package com.matheusluizago.backend.dto.clienteDto;

public record ClienteResponseDto (
        Integer id,
        String nome,
        String email,
        String telefone,
        String foto
) { }
