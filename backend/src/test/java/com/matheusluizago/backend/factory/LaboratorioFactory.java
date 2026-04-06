package com.matheusluizago.backend.factory;

import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.model.Laboratorio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LaboratorioFactory {

    private static final Integer DEFAULT_ID = 1;
    private static final String DEFAULT_NOME = "Laboratório A";
    private static final String DEFAULT_ENDERECO = "Endereço A";
    private static final String DEFAULT_CNPJ = "11222333000181";
    private static final String DEFAULT_EMAIL = "emailtest@email.com";
    private static final String DEFAULT_TELEFONE = "11999999999";

    public static Laboratorio createValidLaboratorio(){
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(DEFAULT_ID);
        laboratorio.setNome(DEFAULT_NOME);
        laboratorio.setEndereco(DEFAULT_ENDERECO);
        laboratorio.setCnpj(DEFAULT_CNPJ);
        laboratorio.setEmail(DEFAULT_EMAIL);
        laboratorio.setTelefone(DEFAULT_TELEFONE);
        laboratorio.setDateRegister(LocalDateTime.now());
        return laboratorio;
    }

    public static Laboratorio createValidLaboratorioWithoutId(){
        Laboratorio laboratorio = createValidLaboratorio();
        laboratorio.setId(null);
        return laboratorio;
    }

    public static Laboratorio createInvalidLaboratorioNome(){
        Laboratorio laboratorio = createValidLaboratorio();
        laboratorio.setNome("");
        return laboratorio;
    }

    public static Laboratorio createInvalidLaboratorioEndereco(){
        Laboratorio laboratorio = createValidLaboratorio();
        laboratorio.setEndereco("");
        return laboratorio;
    }

    public static Laboratorio createInvalidLaboratorioCnpj(){
        Laboratorio laboratorio = createValidLaboratorio();
        laboratorio.setCnpj("12345");
        return laboratorio;
    }

    //REGISTER DTO
    public static LaboratorioRegisterDto createValidLaboratorioRegisterDto(){
        return new LaboratorioRegisterDto(
                DEFAULT_NOME,
                DEFAULT_ENDERECO,
                DEFAULT_CNPJ,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioRegisterDto createInvalidLaboratorioRegisterDtoNome(){
        return new LaboratorioRegisterDto(
                "",
                DEFAULT_ENDERECO,
                DEFAULT_CNPJ,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioRegisterDto createInvalidLaboratorioRegisterDtoEndereco(){
        return new LaboratorioRegisterDto(
                DEFAULT_NOME,
                "",
                DEFAULT_CNPJ,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioRegisterDto createInvalidLaboratorioRegisterDtoCnpj(){
        return new LaboratorioRegisterDto(
                DEFAULT_NOME,
                DEFAULT_ENDERECO,
                "12456",
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    //UPDATE DTO
    public static LaboratorioUpdateDto createValidLaboratorioUpdateDto(){
        return new LaboratorioUpdateDto(
                DEFAULT_NOME,
                DEFAULT_ENDERECO,
                DEFAULT_CNPJ,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioUpdateDto createValidLaboratorioUpdateDtoFewFields(){
        return new LaboratorioUpdateDto(
                "",
                DEFAULT_ENDERECO,
                "",
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioUpdateDto createInvalidLaboratorioUpdateDtoCnpj(){
        return new LaboratorioUpdateDto(
                DEFAULT_NOME,
                DEFAULT_ENDERECO,
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }

    public static LaboratorioResponseDto createValidLaboratorioResponseDto(){
        return new LaboratorioResponseDto(
                DEFAULT_ID,
                DEFAULT_NOME,
                DEFAULT_ENDERECO,
                DEFAULT_CNPJ,
                DEFAULT_EMAIL,
                DEFAULT_TELEFONE
        );
    }
}
