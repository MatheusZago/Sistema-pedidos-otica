package com.matheusluizago.backend.service;

import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository repository;

    public LaboratorioService(LaboratorioRepository repository) {
        this.repository = repository;
    }

    public Laboratorio save(Laboratorio laboratorio) {
        return repository.save(laboratorio);
    }

    public Optional<Laboratorio> getById(int id) {
        return repository.findById(id);
    }
}
