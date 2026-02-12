package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.mapper.LaboratorioMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.specs.LaboratorioSpecs;
import com.matheusluizago.backend.validator.LaboratorioValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository repository;
    private final LaboratorioMapper mapper;
    private final LaboratorioValidator validator;

    public LaboratorioService(LaboratorioRepository repository, LaboratorioMapper mapper, LaboratorioValidator validator) {
        this.mapper = mapper;
        this.repository = repository;
        this.validator = validator;
    }

    public LaboratorioResponseDto save(LaboratorioRegisterDto labDto) {

        Laboratorio lab = mapper.toEntity(labDto);

        validator.validate(lab);
        repository.save(lab);

        return mapper.toDto(lab);
    }

    public List<LaboratorioResponseDto> search(
            Integer id,
            String nome,
            String endereco,
            String cnpj
    ){
        Specification<Laboratorio> spec = Specification
                .where(LaboratorioSpecs.idEqual(id))
                .and(LaboratorioSpecs.nomeLike(nome))
                .and(LaboratorioSpecs.enderecoLike(endereco))
                .and(LaboratorioSpecs.cnpjLike(cnpj));

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public LaboratorioResponseDto update(Integer id, LaboratorioUpdateDto dto){

        Laboratorio laboratorio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrado"));

        validator.validate(laboratorio);

        mapper.updateLab(laboratorio, dto);

        Laboratorio atualizado = repository.save(laboratorio);

        return mapper.toDto(atualizado);

    }

    public void delete(Integer id){
        Laboratorio laboratorio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrado."));

        repository.delete(laboratorio);
    }
}
