package com.matheusluizago.backend.dto.laboratorioDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LaboratorioRegisterDto(
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, max = 100, message = "Nome inválido.")
        String nome,
        @NotBlank(message = "Endereço é obrigatório.")
        @Size(min = 3, max = 100, message = "Laboratório inválido.")
        String endereco,
        @NotBlank(message = "Endereço é obrigatório.")
        @Size(min = 14, max = 14, message = "CNPJ inválido.")
        String cnpj
) {
}
