package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.model.Laboratorio;
import org.springframework.stereotype.Component;

@Component
public class LaboratorioMapper {

    public Laboratorio toEntity(LaboratorioRegisterDto dto){
        Laboratorio lab = new Laboratorio();

        lab.setNome(dto.nome());
        lab.setEndereco(dto.endereco());
        lab.setCnpj(dto.cnpj());
        lab.setEmail(dto.email());
        lab.setTelefone(dto.telefone());

        return lab;
    }

    public LaboratorioResponseDto toDto(Laboratorio lab){

        return new LaboratorioResponseDto(
                lab.getId(),
                lab.getNome(),
                lab.getEndereco(),
                lab.getCnpj(),
                lab.getEmail(),
            lab.getTelefone());
    }

    public void updateLab(Laboratorio lab, LaboratorioUpdateDto dto){
        if(dto.nome() != null){
            lab.setNome(dto.nome());
        }
        if(dto.endereco() != null) {
            lab.setEndereco(dto.endereco());
        }

        if(dto.cnpj() != null){
            lab.setCnpj(dto.cnpj());
        }

        if(dto.email() != null) {
            lab.setEmail(dto.email());
        }

        if(dto.telefone() != null) {
            lab.setTelefone(dto.telefone());
        }

    }
}
