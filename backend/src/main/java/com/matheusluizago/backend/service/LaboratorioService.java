package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.mapper.LaboratorioMapper;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository repository;
    private final LaboratorioMapper mapper;

    public LaboratorioService(LaboratorioRepository repository, LaboratorioMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Laboratorio save(LaboratorioRegisterDto labDto) {

        Laboratorio lab = mapper.toEntity(labDto);

        return repository.save(lab);
    }

    public Optional<Laboratorio> getById(int id) {
        return repository.findById(id);
    }
}
