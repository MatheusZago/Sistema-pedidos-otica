package com.matheusluizago.backend.dto.laboratorioDto;

import jakarta.validation.constraints.Size;

public record LaboratorioUpdateDto(
        @Size(min = 3, max = 100, message = "Nome inválido.")
        String nome,
        @Size(min = 3, max = 100, message = "Endereço inválido.")
        String endereco,
        @Size(min = 14, max = 14, message = "CNPJ inválido.")
        String cnpj
) {
}
