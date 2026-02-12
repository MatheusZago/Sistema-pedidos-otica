package com.matheusluizago.backend.dto.pedidoDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PedidoRegisterDto(
        @NotBlank(message = "Cliente é obrigatório.")
        Integer clienteId,
        @NotBlank(message = "Laboratório é obrigatório.")
        Integer laboratorioId,
        @NotBlank(message = "Lente é obrigatória.")
        Integer lenteId,
        @Size(min = 3, max = 100, message = "Armação inválida.")
        String armacao,
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
        BigDecimal dnp

) {
}
