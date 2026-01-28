package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.ClienteRegisterDto;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper){
        this.mapper = mapper;
        this.repository = repository;
    }

    public Cliente save(ClienteRegisterDto clienteDto) {

        Cliente cliente = mapper.toEntity(clienteDto);
        return repository.save(cliente);
    }

    public Optional<Cliente> getById(int id) {
        return Optional.of(repository.getById(id));
    }

    public Optional<Cliente> getByNome(String nome) {
        return Optional.of(repository.getByNome(nome));
    }
}
