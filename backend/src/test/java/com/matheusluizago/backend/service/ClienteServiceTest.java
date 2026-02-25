package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.mapper.ClienteMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.validator.ClienteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

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

    private Cliente cliente;
    private ClienteRegisterDto registerDto;
    private ClienteResponseDto responseDto;
    private ClienteUpdateDto updateDto;
    private Integer validId = 1;
    private Integer invalidId = 123123123;

    @BeforeEach
    void setUp(){
        cliente = ClienteFactory.createValidCliente();
        registerDto = ClienteFactory.createValidRegisterClienteDto();
        responseDto = ClienteFactory.createValidClienteResponseDto();
        updateDto = ClienteFactory.createValidClienteUpdateDto();
    }

    @Test
    void saveCliente_WithValidData_ShouldSave(){

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

        when(mapper.toEntity(registerDto)).thenReturn(cliente);

        doThrow(new DuplicateRegisterException("Cliente já registrado."))
                .when(validator).validate(cliente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.save(registerDto));

        verify(repository, never()).save(any());
    }

    @Test
    void searchCliente_WithFilters_ShouldReturnList() {

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

    @Test
    void updateCliente_WithValiData_ShouldReturnUpdatedClient(){

        when(repository.findById(validId)).thenReturn(Optional.of(cliente));
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDto(cliente)).thenReturn(responseDto);

        ClienteResponseDto test = service.update(validId, updateDto);

        verify(repository).findById(validId);
        verify(mapper).updateCliente(cliente, updateDto);
        verify(validator).validate(cliente);
        verify(repository).save(cliente);
        verify(mapper).toDto(cliente);
        assertNotNull(test);
        assertEquals(responseDto, test);
    }

    @Test
    void updateCliente_WithDuplicateEmail_ShouldThrowException(){

        when(repository.findById(validId)).thenReturn(Optional.of(cliente));

        doThrow(new DuplicateRegisterException("Email já em uso!"))
                .when(validator).validate(cliente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.update(validId, updateDto)
        );

        verify(repository).findById(validId);
        verify(mapper).updateCliente(cliente, updateDto);
        verify(validator).validate(cliente);
        verify(repository, never()).save(any());

    }

    @Test
    void updateCliente_WhenIdNotFound_ShouldThroewException(){

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(invalidId, ClienteFactory.createValidClienteUpdateDto())
        );

        verify(repository).findById(invalidId);
        verify(repository, never()).save(any());

    }

    @Test
    void deleteCliente_WithValidId_ShouldDelete(){

        when(repository.findById(validId)).thenReturn(Optional.of(cliente));

        service.delete(validId);

        verify(repository).findById(validId);
        verify(repository).delete(cliente);
    }

    @Test
    void deleteCliente_WithInvalidId_ShouldThrowException(){

        when(repository.findById(validId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(validId)
        );

        verify(repository).findById(validId);
        verify(repository, never()).delete(any(Cliente.class));
    }


}
