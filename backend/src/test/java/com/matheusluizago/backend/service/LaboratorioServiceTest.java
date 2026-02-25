package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.mapper.LaboratorioMapper;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.validator.LaboratorioValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LaboratorioServiceTest {

    @Mock
    private LaboratorioRepository repository;

    @Mock
    private LaboratorioMapper mapper;

    @Mock
    private LaboratorioValidator validator;

    @InjectMocks
    private LaboratorioService service;

    @Test
    void saveLaboratorio_WithValidData_ShouldSave(){
        LaboratorioRegisterDto registerDto = LaboratorioFactory.createValidLaboratorioRegisterDto();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();
        LaboratorioResponseDto responseDto = LaboratorioFactory.createValidLaboratorioResponseDto();

        //Mockand comportamento
        when(mapper.toEntity(registerDto)).thenReturn(lab);
        when(repository.save(lab)).thenReturn(lab);
        when(mapper.toDto(lab)).thenReturn(responseDto);

        LaboratorioResponseDto test = service.save(registerDto);

        assertNotNull(test);

        //Está vendo se esses métodos foram chamados
        verify(mapper).toEntity(registerDto);
        verify(validator).validate(lab);
        verify(repository).save(lab);
        verify(mapper).toDto(lab);
    }


    @Test
    void saveLaboratorio_WithValidatorException_ShouldNotSave(){
        LaboratorioRegisterDto registerDto = LaboratorioFactory.createValidLaboratorioRegisterDto();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();

        when(mapper.toEntity(registerDto)).thenReturn(lab);

        doThrow(new DuplicateRegisterException("Laboratorio já registrado."))
                .when(validator).validate(lab);

        assertThrows(DuplicateRegisterException.class,
                () -> service.save(registerDto));

        verify(repository, never()).save(any());
    }

    @Test
    void searchLaboratorio_WithFilters_ShouldReturnList() {

        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();
        LaboratorioResponseDto responseDto = LaboratorioFactory.createValidLaboratorioResponseDto();

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(lab));
        when(mapper.toDto(lab)).thenReturn(responseDto);

        List<LaboratorioResponseDto> test = service.search(1, "Laboratório A", "ndereço A","12345678000195");

        assertNotNull(test);
        assertEquals(1, test.size());
        assertEquals(responseDto, test.get(0));

        verify(repository).findAll(any(Specification.class));
        verify(mapper).toDto(lab);
    }


    @Test
    void searchLaboratorio_WhenFilterMatchesNothing_ShouldReturnEmptyList() {

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<LaboratorioResponseDto> result = service.search(
                null, "NomeInexistente", null, null
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateLaboratorio_WithValiData_ShouldReturnUpdatedClient(){
        Integer id = 1;

        LaboratorioUpdateDto updateDto = LaboratorioFactory.createValidLaboratorioUpdateDto();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();
        LaboratorioResponseDto responseDto = LaboratorioFactory.createValidLaboratorioResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(lab));
        when(repository.save(lab)).thenReturn(lab);
        when(mapper.toDto(lab)).thenReturn(responseDto);

        LaboratorioResponseDto test = service.update(id, updateDto);

        verify(repository).findById(id);
        verify(mapper).updateLab(lab, updateDto);
        verify(validator).validate(lab);
        verify(repository).save(lab);
        verify(mapper).toDto(lab);
        assertNotNull(test);
        assertEquals(responseDto, test);
    }

    @Test
    void updateLaboratorio_WithDuplicateEmail_ShouldThrowException(){
        Integer id = 1;

        LaboratorioUpdateDto updateDto = LaboratorioFactory.createValidLaboratorioUpdateDto();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();

        when(repository.findById(id)).thenReturn(Optional.of(lab));

        doThrow(new DuplicateRegisterException("CNPJ já em uso!"))
                .when(validator).validate(lab);

        assertThrows(DuplicateRegisterException.class,
                () -> service.update(id, updateDto)
        );

        verify(repository).findById(id);
        verify(mapper).updateLab(lab, updateDto);
        verify(validator).validate(lab);
        verify(repository, never()).save(any());

    }

    @Test
    void updateLaboratorio_WhenIdNotFound_ShouldThrowException(){
        Integer id = 12312;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(id, LaboratorioFactory.createValidLaboratorioUpdateDto())
        );

        verify(repository).findById(id);
        verify(repository, never()).save(any());

    }

    @Test
    void deleteLaboratorio_WithValidId_ShouldDelete(){
        Integer id = 1;
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();

        when(repository.findById(id)).thenReturn(Optional.of(lab));

        service.delete(id);

        verify(repository).findById(id);
        verify(repository).delete(lab);
    }

    @Test
    void deleteLaboratorio_WithInvalidId_ShouldThrowException(){
        Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(id)
        );

        verify(repository).findById(id);
        verify(repository, never()).delete(any(Laboratorio.class));
    }


}
