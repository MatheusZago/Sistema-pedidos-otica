package com.matheusluizago.backend.dto.usuarioDto;

import com.matheusluizago.backend.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateDto(

        @Size(min = 3, max = 50,
                message = "Username deve ter entre 3 e 50 caracteres.")
        String username,

        @Email(message = "Email inválido.")
        @Size(max = 100,
                message = "Email deve ter no máximo 100 caracteres.")
        String email,

        @Size(min = 6, max = 255,
                message = "Senha deve ter entre 6 e 255 caracteres.")
        String senha,

        Role role
) {
}