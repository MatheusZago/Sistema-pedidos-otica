package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.mapper.LenteMapper;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.validator.LenteValidator;
import org.springframework.stereotype.Service;

@Service
public class LenteService {

    private final LenteRepository repository;
    private final LenteMapper mapper;
    private final LenteValidator validator;

    public LenteService(LenteRepository repository, LenteMapper mapper, LenteValidator validator){
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    public Lente save(LenteRegisterDto dto){

        Lente lente = mapper.toEntity(dto);

        validator.validate(lente);

        return repository.save(lente);



    }


}
