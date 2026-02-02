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
        BigDecimal custo,
        String armacao,
        BigDecimal od,
        BigDecimal oe,
        BigDecimal ad,
        BigDecimal dnp,
        String tratamento,
        String tipoLente,
        LocalDateTime createdAt
) {
}
