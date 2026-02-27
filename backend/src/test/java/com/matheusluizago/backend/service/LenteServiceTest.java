package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.mapper.LenteMapper;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.validator.LenteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LenteServiceTest {

    @Mock
    private LenteRepository repository;

    @Mock
    private LenteMapper mapper;

    @Mock
    private LenteValidator validator;

    @InjectMocks
    private LenteService service;

    private Lente lente;
    private LenteRegisterDto registerDto;
    private LenteResponseDto responseDto;
    private LenteUpdateDto updateDto;
    private final Integer validId = 1;
    private final Integer invalidId = 123123123;

    @BeforeEach
    void setUp(){
        lente = LenteFactory.createValidLente();
        registerDto = LenteFactory.createValidLenteRegisterDto();
        responseDto = LenteFactory.createValidLenteResponseDto();
        updateDto = LenteFactory.createValidLenteUpdateDto();
    }

    @Test
    void saveLente_WithValidData_ShouldSave(){

        //Mockand comportamento
        when(mapper.toEntity(registerDto)).thenReturn(lente);
        when(repository.save(lente)).thenReturn(lente);
        when(mapper.toDto(lente)).thenReturn(responseDto);

        LenteResponseDto test = service.save(registerDto);

        assertNotNull(test);

        //Está vendo se esses métodos foram chamados
        verify(mapper).toEntity(registerDto);
        verify(validator).validate(lente);
        verify(repository).save(lente);
        verify(mapper).toDto(lente);
    }

    @Test
    void saveLente_WithValidatorException_ShouldNotSave(){

        when(mapper.toEntity(registerDto)).thenReturn(lente);

        doThrow(new DuplicateRegisterException("Lente já registrado."))
                .when(validator).validate(lente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.save(registerDto));

        verify(repository, never()).save(any());
    }

    @Test
    void searchLente_WithFilters_ShouldReturnList() {

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(lente));
        when(mapper.toDto(lente)).thenReturn(responseDto);

        List<LenteResponseDto> test = service.search(1, "Lente A", BigDecimal.valueOf(50)
                ,"Tratamento A)", "Indice A", BigDecimal.valueOf(80));

        assertNotNull(test);
        assertEquals(1, test.size());
        assertEquals(responseDto, test.get(0));

        verify(repository).findAll(any(Specification.class));
        verify(mapper).toDto(lente);
    }


    @Test
    void searchLente_WhenFilterMatchesNothing_ShouldReturnEmptyList() {

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<LenteResponseDto> result = service.search(
                null, "LenteInexistente", null, null, null, null
        );

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateLente_WithValiData_ShouldReturnUpdatedClient(){

        when(repository.findById(validId)).thenReturn(Optional.of(lente));
        when(repository.save(lente)).thenReturn(lente);
        when(mapper.toDto(lente)).thenReturn(responseDto);

        LenteResponseDto test = service.update(validId, updateDto);

        verify(repository).findById(validId);
        verify(mapper).updateLente(lente, updateDto);
        verify(validator).validate(lente);
        verify(repository).save(lente);
        verify(mapper).toDto(lente);
        assertNotNull(test);
        assertEquals(responseDto, test);
    }

    @Test
    void updateCliente_WithDuplicateEmail_ShouldThrowException(){

        when(repository.findById(validId)).thenReturn(Optional.of(lente));

        doThrow(new DuplicateRegisterException("Lente já registrada!"))
                .when(validator).validate(lente);

        assertThrows(DuplicateRegisterException.class,
                () -> service.update(validId, updateDto)
        );

        verify(repository).findById(validId);
        verify(mapper).updateLente(lente, updateDto);
        verify(validator).validate(lente);
        verify(repository, never()).save(any());

    }

    @Test
    void updateLente_WhenIdNotFound_ShouldThroewException(){

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(invalidId, LenteFactory.createValidLenteUpdateDto())
        );

        verify(repository).findById(invalidId);
        verify(repository, never()).save(any());

    }

    @Test
    void deleteLente_WithValidId_ShouldDelete(){

        when(repository.findById(validId)).thenReturn(Optional.of(lente));

        service.delete(validId);

        verify(repository).findById(validId);
        verify(repository).delete(lente);
    }

    @Test
    void deleteLente_WithInvalidId_ShouldThrowException(){

        when(repository.findById(validId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(validId)
        );

        verify(repository).findById(validId);
        verify(repository, never()).delete(any(Lente.class));
    }
}
