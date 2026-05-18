package com.matheusluizago.backend.dto.usuarioDto;

import com.matheusluizago.backend.model.enums.Role;

import java.time.LocalDateTime;

public record UsuarioResponseDto(

        Integer id,

        String username,

        String email,

        Role role,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}