package com.matheusluizago.backend.dto.clienteDto;

public record ClienteRegisterDto(
        String nome,
        String telefone,
        String email,
        String foto
) {}
