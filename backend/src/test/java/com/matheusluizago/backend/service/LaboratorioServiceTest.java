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

    private Laboratorio lab;
    private LaboratorioRegisterDto registerDto;
    private LaboratorioUpdateDto updateDto;
    private LaboratorioResponseDto responseDto;
    private Integer validId = 1;
    private Integer invalidId = 123123123;

    @BeforeEach
    void setUp() {
        lab = LaboratorioFactory.createValidLaboratorio();
        registerDto = LaboratorioFactory.createValidLaboratorioRegisterDto();
        updateDto = LaboratorioFactory.createValidLaboratorioUpdateDto();
        responseDto = LaboratorioFactory.createValidLaboratorioResponseDto();
    }

    @Test
    void saveLaboratorio_WithValidData_ShouldSave(){

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

        when(mapper.toEntity(registerDto)).thenReturn(lab);

        doThrow(new DuplicateRegisterException("Laboratorio já registrado."))
                .when(validator).validate(lab);

        assertThrows(DuplicateRegisterException.class,
                () -> service.save(registerDto));

        verify(repository, never()).save(any());
    }

    @Test
    void searchLaboratorio_WithFilters_ShouldReturnList() {

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(lab));
        when(mapper.toDto(lab)).thenReturn(responseDto);

        List<LaboratorioResponseDto> test = service.search(1, "Laboratório A", "ndereço A","12345678000195", null, null);

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
                null, "NomeInexistente", null, null, null, null
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateLaboratorio_WithValiData_ShouldReturnUpdatedClient(){


        when(repository.findById(validId)).thenReturn(Optional.of(lab));
        when(repository.save(lab)).thenReturn(lab);
        when(mapper.toDto(lab)).thenReturn(responseDto);

        LaboratorioResponseDto test = service.update(validId, updateDto);

        verify(repository).findById(validId);
        verify(mapper).updateLab(lab, updateDto);
        verify(validator).validate(lab);
        verify(repository).save(lab);
        verify(mapper).toDto(lab);
        assertNotNull(test);
        assertEquals(responseDto, test);
    }

    @Test
    void updateLaboratorio_WithDuplicateEmail_ShouldThrowException(){

        LaboratorioUpdateDto updateDto = LaboratorioFactory.createValidLaboratorioUpdateDto();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();

        when(repository.findById(validId)).thenReturn(Optional.of(lab));

        doThrow(new DuplicateRegisterException("CNPJ já em uso!"))
                .when(validator).validate(lab);

        assertThrows(DuplicateRegisterException.class,
                () -> service.update(validId, updateDto)
        );

        verify(repository).findById(validId);
        verify(mapper).updateLab(lab, updateDto);
        verify(validator).validate(lab);
        verify(repository, never()).save(any());

    }

    @Test
    void updateLaboratorio_WhenIdNotFound_ShouldThrowException(){

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(invalidId, LaboratorioFactory.createValidLaboratorioUpdateDto())
        );

        verify(repository).findById(invalidId);
        verify(repository, never()).save(any());

    }

    @Test
    void deleteLaboratorio_WithValidId_ShouldDelete(){

        when(repository.findById(validId)).thenReturn(Optional.of(lab));

        service.delete(validId);

        verify(repository).findById(validId);
        verify(repository).delete(lab);
    }

    @Test
    void deleteLaboratorio_WithInvalidId_ShouldThrowException(){

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(invalidId)
        );

        verify(repository).findById(invalidId);
        verify(repository, never()).delete(any(Laboratorio.class));
    }


}
