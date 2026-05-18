package com.matheusluizago.backend.dto.usuarioDto;

public record AuthResponseDto(

        String token,

        String type,

        String username,

        String role
) {
}