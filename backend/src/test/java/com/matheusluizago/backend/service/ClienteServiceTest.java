package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.validator.ClienteValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @Mock
    private ClienteValidator validator;

    @InjectMocks
    private ClienteService service;

    @Test
    void saveCliente_WithValidData_ReturnCliente(){
        ClienteRegisterDto registerDto = ClienteFactory.createValidRegisterClienteDto();
        Cliente cliente = ClienteFactory.createValidClienteWithoutId();
        ClienteResponseDto responseDto = ClienteFactory.createValidClienteResponseDto();

        //Mockand comportamento
        when(mapper.toEntity(registerDto)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDto(cliente)).thenReturn(responseDto);

        ClienteResponseDto test = service.save(registerDto);

        assertNotNull(test);

        //Está vendo se esses métodos foram chamados
        verify(mapper).toEntity(registerDto);
        verify(validator).validate(cliente);
        verify(repository).save(cliente);
        verify(mapper).toDto(cliente);
    }

}
