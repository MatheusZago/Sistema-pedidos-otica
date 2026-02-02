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
        @NotBlank(message = "Custo é obrigatório.")
        @Digits(integer = 4, fraction = 2, message = "Formato de Custo inválido")
        BigDecimal custo,
        @Size(min = 3, max = 100, message = "Armação inválida.")
        String armacao,
        @Digits(integer = 1, fraction = 2, message = "Formato de Od inválido")
        BigDecimal od,
        @Digits(integer = 1, fraction = 2, message = "Formato de Oe inválido")
        BigDecimal oe,
        @Digits(integer = 1, fraction = 2, message = "Formato de Ad inválido")
        BigDecimal ad,
        @Digits(integer = 1, fraction = 2, message = "Formato de DNP inválido")
        BigDecimal dnp,
        @Size(min = 3, max = 100, message = "Tratamento inválido.")
        String tratamento,
        @Size(min = 3, max = 100, message = "Tipo de lente inválido.")
        String tipoLente
) {
}
