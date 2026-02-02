package com.matheusluizago.backend.dto.clienteDto;

public record ClienteUpdateDto(
        String nome,
        String telefone,
        String email,
        String foto
) {}
