package com.matheusluizago.backend.dto.lenteDto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LenteUpdateDto(
        @Size(min = 3, max = 100, message = "Tipo de lente invalido.")
        String tipoLente,

        @Digits(integer = 1000, fraction = 2, message = "Formato do custo inválido")
        BigDecimal custo,

        @Size(min = 3, max = 100, message = "Tratamento invalido.")
        String tratamento,

        @Size(min = 3, max = 100, message = "Indice invalido.")
        String indice,

        @Digits(integer = 1000, fraction = 2, message = "Formato do valor da venda inválido")
        BigDecimal valorVenda
) {
}
