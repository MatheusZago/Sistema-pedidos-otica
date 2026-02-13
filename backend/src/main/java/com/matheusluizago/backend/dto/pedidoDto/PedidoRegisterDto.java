package com.matheusluizago.backend.dto.pedidoDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRegisterDto(
        @NotNull(message = "Cliente é obrigatório.")
        Integer clienteId,
        @NotNull(message = "Laboratório é obrigatório.")
        Integer laboratorioId,
        @NotNull(message = "Lente é obrigatória.")
        Integer lenteId,
        @NotBlank
        @Size(min = 3, max = 100, message = "Armação inválida.")
        String armacao,
        @Size(min = 3, max = 200, message = "Link da imagem invalido.")
        String armacaoImg,
        @NotNull
        @Digits(integer = 3, fraction = 2, message = "Formato de Od de perto inválido")
        BigDecimal odPerto,
        @NotNull
        @Digits(integer = 3, fraction = 2, message = "Formato de Od de longe perto inválido")
        BigDecimal odLonge,
        @NotNull
        @Digits(integer = 3, fraction = 2, message = "Formato de Oe de perto inválido")
        BigDecimal oePerto,
        @NotNull
        @Digits(integer = 3, fraction = 2, message = "Formato de Oe de longe inválido")
        BigDecimal oeLonge,
        @NotNull
        @Digits(integer = 3, fraction = 2, message = "Formato de Ad inválido")
        BigDecimal ad,
        @NotNull
        @Digits(integer = 1, fraction = 2, message = "Formato de DNP inválido")
        BigDecimal dnp,

        LocalDateTime dataEntrega

) {
}
