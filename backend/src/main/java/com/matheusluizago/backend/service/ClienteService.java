package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.specs.ClienteSpecs;
import com.matheusluizago.backend.validator.ClienteValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final ClienteValidator validator;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper, ClienteValidator validator){
        this.mapper = mapper;
        this.repository = repository;
        this.validator = validator;
    }

    public Cliente save(ClienteRegisterDto clienteDto) {

        Cliente cliente = mapper.toEntity(clienteDto);

        validator.validate(cliente);
        return repository.save(cliente);
    }

    public List<ClienteResponseDto> search(
            Integer id,
            String nome,
            String telefone,
            String email
    ) {
        Specification<Cliente> spec = Specification
                .where(ClienteSpecs.idEqual(id))
                .and(ClienteSpecs.nomeLike(nome))
                .and(ClienteSpecs.telefoneLike(telefone))
                .and(ClienteSpecs.emailLike(email));

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Transactional
    public ClienteResponseDto update(Integer id, ClienteUpdateDto dto){

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        validator.validate(cliente);

        mapper.updateCliente(cliente, dto);

        Cliente atualizado = repository.save(cliente);

        return mapper.toDto(atualizado);

    }
}
