package com.matheusluizago.backend.dto.laboratorioDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record LaboratorioRegisterDto(
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, max = 120, message = "Nome inválido.")
        String nome,

        @NotBlank(message = "Endereço é obrigatório.")
        @Size(min = 3, max = 100, message = "Laboratório inválido.")
        String endereco,

        @NotBlank(message = "CNPJ é obrigatório.")
        @CNPJ(message = "CNPJ inválido.")
        String cnpj,

        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @Pattern(
                regexp = "^\\+?[0-9()\\-\\s]{8,20}$",
                message = "Telefone inválido"
        )
        String telefone
) {
}
