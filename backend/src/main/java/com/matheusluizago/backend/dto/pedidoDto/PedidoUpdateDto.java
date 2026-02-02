package com.matheusluizago.backend.dto.pedidoDto;

import java.math.BigDecimal;

public record PedidoUpdateDto(
        Integer clienteId,
        Integer laboratorioId,
        BigDecimal custo,
        String armacao,
        BigDecimal od,
        BigDecimal oe,
        BigDecimal ad,
        BigDecimal dnp,
        String tratamento,
        String tipoLente
) {
}
