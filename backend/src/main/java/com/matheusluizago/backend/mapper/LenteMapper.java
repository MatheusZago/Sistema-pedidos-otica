package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.model.Lente;
import org.springframework.stereotype.Component;

@Component
public class LenteMapper {

    public Lente toEntity(LenteRegisterDto dto){
        Lente lente = new Lente();

        lente.setTipoLente(dto.tipoLente());
        lente.setCusto(dto.custo());
        lente.setIndice(dto.indice());
        lente.setTratamento(dto.tratamento());
        lente.setValorVenda(dto.valorVenda());

        return lente;
    }

    public LenteResponseDto toDto(Lente lente){
        return new LenteResponseDto(
                lente.getId(),
                lente.getTipoLente(),
                lente.getCusto(),
                lente.getTratamento(),
                lente.getIndice(),
                lente.getValorVenda()
        );
    }

    public void updateLente(Lente lente, LenteUpdateDto dto){
        if(dto.tipoLente() != null){
            lente.setTipoLente(dto.tipoLente());
        }

        if(dto.custo() != null){
            lente.setCusto(dto.custo());
        }

        if(dto.tratamento() != null){
            lente.setTratamento(dto.tratamento());
        }

        if(dto.indice() != null){
            lente.setIndice(dto.indice());
        }

        if(dto.valorVenda() != null){
            lente.setValorVenda(dto.valorVenda());
        }

    }

}
