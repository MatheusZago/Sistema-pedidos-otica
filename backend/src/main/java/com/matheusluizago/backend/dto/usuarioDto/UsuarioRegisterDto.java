package com.matheusluizago.backend.dto.usuarioDto;

import com.matheusluizago.backend.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRegisterDto(

        @NotBlank(message = "Username é obrigatório.")
        @Size(min = 3, max = 50,
                message = "Username deve ter entre 3 e 50 caracteres.")
        String username,

        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        @Size(max = 100,
                message = "Email deve ter no máximo 100 caracteres.")
        String email,

        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 6, max = 255,
                message = "Senha deve ter entre 6 e 255 caracteres.")
        String senha,

        @NotNull(message = "Role é obrigatória.")
        Role role
) {
}