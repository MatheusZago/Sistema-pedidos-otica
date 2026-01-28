package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.LaboratorioResponseDto;
import com.matheusluizago.backend.model.Laboratorio;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioMapper {

    public Laboratorio toEntity(LaboratorioRegisterDto dto){
        Laboratorio lab = new Laboratorio();

        lab.setNome(dto.nome());
        lab.setEndereco(dto.endereco());

        return lab;
    }

    public LaboratorioResponseDto toDto(Laboratorio lab){

        return new LaboratorioResponseDto(
                lab.getId(),
                lab.getNome(),
                lab.getEndereco());
    }
}
