package com.matheusluizago.backend.dto.pedidoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDto(
        Integer id,
        Integer cliente,
        String clienteNome,
        String clienteFoto,
        Integer laboratorio,
        String laboratorioNome,
        Integer lente,
        String tipoLente,
        BigDecimal custo,
        String tratamento,
        String indice,
        BigDecimal valorVenda,
        String armacao,
        BigDecimal odPerto,
        BigDecimal odLonge,
        BigDecimal oePerto,
        BigDecimal oeLonge,
        BigDecimal ad,
        BigDecimal dnp,
        LocalDateTime createdAt
) {
}
