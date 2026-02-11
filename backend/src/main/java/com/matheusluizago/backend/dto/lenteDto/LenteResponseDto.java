package com.matheusluizago.backend.dto.lenteDto;

import java.math.BigDecimal;

public record LenteResponseDto(
        Integer id,
        String tipoLente,
        BigDecimal custo,
        String tratamento,
        String indice,
        BigDecimal valorVenda
) {
}
