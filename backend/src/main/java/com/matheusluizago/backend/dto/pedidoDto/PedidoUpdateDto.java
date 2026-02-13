package com.matheusluizago.backend.dto.pedidoDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoUpdateDto(
        Integer clienteId,
        Integer laboratorioId,
        Integer lenteId,
        @Size(min = 3, max = 100, message = "Armação inválida.")
        String armacao,
        @Size(min = 3, max = 100, message = "Link da imagem da armação inválida.")
        String armacaoImg,
        @Digits(integer = 1, fraction = 2, message = "Formato de Od de perto inválido")
        BigDecimal odPerto,
        @Digits(integer = 1, fraction = 2, message = "Formato de Od de longe perto inválido")
        BigDecimal odLonge,
        @Digits(integer = 1, fraction = 2, message = "Formato de Oe de perto inválido")
        BigDecimal oePerto,
        @Digits(integer = 1, fraction = 2, message = "Formato de Oe de longe inválido")
        BigDecimal oeLonge,
        @Digits(integer = 1, fraction = 2, message = "Formato de Ad inválido")
        BigDecimal ad,
        @Digits(integer = 1, fraction = 2, message = "Formato de DNP inválido")
        BigDecimal dnp,
        LocalDateTime dataEntrega
) {
}
