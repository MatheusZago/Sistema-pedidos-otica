package com.matheusluizago.backend.dto.clienteDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRegisterDto(
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, max = 100, message = "Nome inválido.")
        String nome,
        @Email(message = "Email inválido.")
        String email,
        @NotBlank(message = "Telefone é obrigatório.")
        @Pattern(
                regexp = "^[0-9]{10,11}$",
                message = "Telefone deve conter apenas números e ter 10 ou 11 dígitos"
        )
        String telefone,
        String foto
) {}
