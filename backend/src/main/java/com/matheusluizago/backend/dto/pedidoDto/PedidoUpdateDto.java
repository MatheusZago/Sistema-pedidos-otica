package com.matheusluizago.backend.dto.pedidoDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PedidoUpdateDto(
        Integer clienteId,
        Integer laboratorioId,
        Integer lenteId,
        @Size(min = 3, max = 100, message = "Armação inválida.")
        String armacao,
        @Digits(integer = 1, fraction = 2, message = "Formato de Od inválido")
        BigDecimal od,
        @Digits(integer = 1, fraction = 2, message = "Formato de Oe inválido")
        BigDecimal oe,
        @Digits(integer = 1, fraction = 2, message = "Formato de Ad inválido")
        BigDecimal ad,
        @Digits(integer = 1, fraction = 2, message = "Formato de DNP inválido")
        BigDecimal dnp
) {
}
