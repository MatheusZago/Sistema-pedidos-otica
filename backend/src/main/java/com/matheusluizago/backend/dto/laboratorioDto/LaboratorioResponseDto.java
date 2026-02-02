package com.matheusluizago.backend.dto.laboratorioDto;

public record LaboratorioResponseDto(
        Integer id,
        String nome,
        String endereco,
        String cnpj
) {
}
