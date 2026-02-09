package com.matheusluizago.backend.service;

import com.matheusluizago.backend.controller.ClienteController;
import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.mapper.LenteMapper;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.repository.specs.LenteSpecs;
import com.matheusluizago.backend.validator.LenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LenteService {

    private final LenteRepository repository;
    private final LenteMapper mapper;
    private final LenteValidator validator;

    private final Logger log = LoggerFactory.getLogger(ClienteController.class);

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

    public List<LenteResponseDto> search(
            Integer id,
            String tipoLente,
            BigDecimal custo,
            String tratamento,
            String indice,
            BigDecimal valorVenda
    ) {

        Specification<Lente> specs = Specification
                .where(LenteSpecs.idEqual(id))
                .and(LenteSpecs.tipoLenteLike(tipoLente))
                .and(LenteSpecs.custoEqual(custo))
                .and(LenteSpecs.tratamentoLike(tratamento))
                .and(LenteSpecs.indiceLike(indice))
                .and(LenteSpecs.valorVendaEqual(valorVenda));

        return repository.findAll(specs)
                .stream()
                .map(mapper::toDto)
                .toList();

    }

    @Transactional
    public LenteResponseDto update(Integer id, LenteUpdateDto dto){

        Lente lente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lente não encontrada"));

        validator.validate(lente);

        mapper.updateLente(lente, dto);

        Lente atualizada = repository.save(lente);

        return mapper.toDto(atualizada);

    }

}
