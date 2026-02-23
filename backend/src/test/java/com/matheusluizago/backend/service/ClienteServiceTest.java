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

    Cliente cliente;
    ClienteResponseDto responseDto;

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

    @Test
    void updateCliente_WithValiData_ShouldReturnUpdatedClient(){
        Integer id = 1;

        ClienteUpdateDto updateDto = ClienteFactory.createValidClienteUpdateDto();
        Cliente cliente = ClienteFactory.createValidCliente();
        ClienteResponseDto responseDto = ClienteFactory.createValidClienteResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(cliente));
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.toDto(cliente)).thenReturn(responseDto);

        ClienteResponseDto test = service.update(id, updateDto);

        verify(repository).findById(id);
        verify(mapper).updateCliente(cliente, updateDto);
        verify(validator).validate(cliente);
        verify(repository).save(cliente);
        verify(mapper).toDto(cliente);
        assertNotNull(test);
        assertEquals(responseDto, test);
    }

    @Test
    void updateCliente_WithDuplicateEmail_ShouldThrowException(){
        Integer id = 1;

        ClienteUpdateDto updateDto = ClienteFactory.createValidClienteUpdateDto();
        Cliente cliente = ClienteFactory.createValidCliente();

        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        doThrow(new DuplicateRegisterException("Email já em uso!"))
                .when(validator).validate(cliente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.update(id, updateDto)
        );

        verify(repository).findById(id);
        verify(mapper).updateCliente(cliente, updateDto);
        verify(validator).validate(cliente);
        verify(repository, never()).save(any());

    }

    @Test
    void updateCliente_WhenIdNotFound_ShouldThroewException(){
        Integer id = 12312;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(id, ClienteFactory.createValidClienteUpdateDto())
        );

        verify(repository).findById(id);
        verify(repository, never()).save(any());

    }

    @Test
    void deleteCliente_WithValidId_ShouldDelete(){
        Integer id = 1;
        Cliente cliente = ClienteFactory.createValidCliente();

        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        service.delete(id);

        verify(repository).findById(id);
        verify(repository).delete(cliente);
    }

    @Test
    void deleteCliente_WithInvalidId_ShouldThrowException(){
        Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(id)
        );

        verify(repository).findById(id);
        verify(repository, never()).delete(any(Cliente.class));
    }


}
