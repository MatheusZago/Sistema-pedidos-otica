package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.ClienteResponseDto;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<ClienteResponseDto> searchByExample(Integer id, String nome, String telefone, String email) {
        var cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("foto")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //Nn precisa ser o falor exato, se tiver lá

        Example<Cliente> clienteExample = Example.of(cliente, matcher); 

        return repository.findAll(clienteExample)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public ClienteResponseDto update(Integer id, ClienteRegisterDto dto){

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clientenão encontrado"));

        mapper.updateCliente(cliente, dto);

        Cliente atualizado = repository.save(cliente);

        return mapper.toDto(atualizado);

    }
}
