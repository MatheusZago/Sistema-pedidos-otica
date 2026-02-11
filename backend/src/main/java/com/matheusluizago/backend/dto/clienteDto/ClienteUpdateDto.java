package com.matheusluizago.backend.dto.clienteDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteUpdateDto(
        @Size(min = 3, max = 100, message = "Nome inválido.")
        String nome,
        @Pattern(
                regexp = "^[0-9]{10,11}$",
                message = "Telefone deve conter apenas números e ter 10 ou 11 dígitos"
        )
        String telefone,
        @Email(message = "Email inválido.")
        String email,
        String foto
) {}
