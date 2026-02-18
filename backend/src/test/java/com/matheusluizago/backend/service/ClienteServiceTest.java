package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void saveCliente_WithValidData_ShouldSave(){
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

    @Test
    void saveCliente_WithValidatorException_ShouldNotSave(){
        ClienteRegisterDto registerDto = ClienteFactory.createValidRegisterClienteDto();
        Cliente cliente = ClienteFactory.createValidClienteWithoutId();

        when(mapper.toEntity(registerDto)).thenReturn(cliente);

        doThrow(new DuplicateRegisterException("Cliente já registrado."))
                .when(validator).validate(cliente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.save(registerDto));

        verify(repository, never()).save(any());
    }

    @Test
    void searchCliente_WithFilters_ShouldReturnList() {

        Cliente cliente = ClienteFactory.createValidCliente();
        ClienteResponseDto responseDto = ClienteFactory.createValidClienteResponseDto();

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(cliente));
        when(mapper.toDto(cliente)).thenReturn(responseDto);

        List<ClienteResponseDto> test = service.search(1, "Matheus", "11912345678","matheus@email.com");

        assertNotNull(test);
        assertEquals(1, test.size());
        assertEquals(responseDto, test.get(0));

        verify(repository).findAll(any(Specification.class));
        verify(mapper).toDto(cliente);
    }


    @Test
    void searchCliente_WhenFilterMatchesNothing_ShouldReturnEmptyList() {

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<ClienteResponseDto> result = service.search(
                null, "NomeInexistente", null, null
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDto(any());
    }


}
