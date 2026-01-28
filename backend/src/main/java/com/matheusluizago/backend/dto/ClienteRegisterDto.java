package com.matheusluizago.backend.dto;

public record ClienteRegisterDto(
        String nome,
        String telefone,
        String email,
        String foto
) {}
