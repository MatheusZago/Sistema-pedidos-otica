package com.matheusluizago.backend.dto.lenteDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LenteRegisterDto(
        @NotNull(message = "Tipo da lente é obrigatório.")
        @Size(min = 3, max = 100, message = "Tipo de lente invalido.")
        String tipoLente,

        @NotNull(message = "Custo é obrigatório.")
        @Digits(integer = 1000, fraction = 2, message = "Formato do custo inválido")
        BigDecimal custo,

        @NotNull(message = "Tratamento é obrigatório.")
        @Size(min = 3, max = 100, message = "Tratamento invalido.")
        String tratamento,

        @NotNull(message = "Indice é obrigatório.")
        @Size(min = 3, max = 100, message = "Indice invalido.")
        String indice,

        @NotNull(message = "Valor da venda é obrigatório.")
        @Digits(integer = 1000, fraction = 2, message = "Formato do valor da venda inválido")
        BigDecimal valorVenda

) {
}
