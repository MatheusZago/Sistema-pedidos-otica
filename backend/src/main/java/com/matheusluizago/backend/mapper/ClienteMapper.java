package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.ClienteResponseDto;
import com.matheusluizago.backend.model.Cliente;
import org.springframework.stereotype.Component;

@Component //Para ser carregado na criação do Spring
public class ClienteMapper {

    public Cliente toEntity(ClienteRegisterDto dto){
        Cliente cliente = new Cliente();

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        return cliente;
    }

    public ClienteResponseDto toDto(Cliente cliente){

        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getFoto());
    }
}
