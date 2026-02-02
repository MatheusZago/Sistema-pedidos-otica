package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
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

    public void updateCliente(Cliente cliente, ClienteRegisterDto dto){

        if(dto.nome() != null) {
            cliente.setNome(dto.nome());
        }

        if(dto.email() != null){
            cliente.setEmail(dto.email());
        }
        if(dto.telefone() != null){
            cliente.setTelefone(dto.telefone());
        }
        if(dto.foto() != null){
            cliente.setFoto(dto.foto());
        }
    }
}
