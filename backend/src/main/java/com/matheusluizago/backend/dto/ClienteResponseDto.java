package com.matheusluizago.backend.dto;

public record ClienteResponseDto (
        Integer id,
        String nome,
        String telefone,
        String email,
        String foto
) { }
