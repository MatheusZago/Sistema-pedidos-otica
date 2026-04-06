package com.matheusluizago.backend.dto.laboratorioDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record LaboratorioUpdateDto(
        @Size(min = 3, max = 100, message = "Nome inválido.")
        String nome,
        @Size(min = 3, max = 150, message = "Endereço inválido.")
        String endereco,
        @CNPJ
        String cnpj,
        @Email(message = "Email inválido.")
        String email,

        @Pattern(
                regexp = "^\\+?[0-9()\\-\\s]{8,20}$",
                message = "Telefone inválido"
        )
        String telefone
) {
}
