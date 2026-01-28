package com.matheusluizago.backend.dto;

import java.math.BigDecimal;

public record PedidoRegisterDto(
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
