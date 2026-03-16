package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteResponseDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.GlobalExceptionHandler;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.service.LenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LenteController.class)
@Import(GlobalExceptionHandler.class)
public class LenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LenteService lenteService;

    private LenteRegisterDto registerDto;
    private LenteRegisterDto nullIndiceRegisterDto;
    private LenteRegisterDto invalidCustoRegisterDto;
    private LenteRegisterDto invalidRegisterDto;
    private LenteRegisterDto invalidTratamentoRegisterDto;
    private LenteRegisterDto invalidIndiceRegisterDto;
    private LenteRegisterDto  invalidValorVendaRegisterDto;


    private LenteResponseDto responseDto;

    private LenteUpdateDto updateDto;

    private final Integer validId = 1;
    private final Integer invalidId = 123123123;

    @BeforeEach
    void setUp() {
        registerDto = LenteFactory.createValidLenteRegisterDto();
        nullIndiceRegisterDto = LenteFactory.createInvalidLenteRegisterDtoIndice();
        invalidCustoRegisterDto = LenteFactory.createInvalidLenteRegisterDtoCusto();
        invalidTratamentoRegisterDto = LenteFactory.createInvalidLenteRegisterDtoTratamento();
        invalidIndiceRegisterDto = LenteFactory.createInvalidLenteRegisterDtoIndice();
        invalidValorVendaRegisterDto = LenteFactory.createInvalidLenteRegisterDtoValorVenda();


        invalidRegisterDto = LenteFactory.createInvalidLenteRegisterDtoCusto();
        responseDto = LenteFactory.createValidLenteResponseDto();

        updateDto = LenteFactory.createValidLenteUpdateDto();
    }

    // ==============================
    // SAVE
    // ==============================

    @Test
    void saveLente_WithValidData_ShouldReturn201() throws Exception {

        when(lenteService.save(any(LenteRegisterDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.tipoLente").value(responseDto.tipoLente()));

        verify(lenteService).save(any(LenteRegisterDto.class));
    }

    @Test
    void saveLente_WithInvalidTipoLente_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any());
    }

    @Test
    void saveLente_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any());
    }

    @Test
    void saveLente_WithDuplicateData_ShouldReturn409() throws Exception {

        when(lenteService.save(any()))
                .thenThrow(new DuplicateRegisterException("Lente já cadastrada"));

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isConflict());

        verify(lenteService).save(any());
    }

    @Test
    void saveLente_WithInvalidCusto_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCustoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any(LenteRegisterDto.class));
    }

    @Test
    void saveLente_WithInvalidTratamento_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTratamentoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any(LenteRegisterDto.class));
    }

    @Test
    void saveLente_WithInvalidIndice_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidIndiceRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any(LenteRegisterDto.class));
    }

    @Test
    void saveLente_WithInvalidValorVenda_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidValorVendaRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any(LenteRegisterDto.class));
    }



    @Test
    void saveLente_WithNullIndice_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nullIndiceRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).save(any(LenteRegisterDto.class));
    }



    @Test
    void searchLente_WithoutParams_ShouldReturn200AndList() throws Exception {

        List<LenteResponseDto> list = List.of(responseDto);

        when(lenteService.search(null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/lentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].tipoLente").value(responseDto.tipoLente()));

        verify(lenteService).search(null, null, null, null, null, null);
    }

    @Test
    void searchLente_WithIdParam_ShouldReturn200() throws Exception {

        List<LenteResponseDto> list = List.of(responseDto);

        when(lenteService.search(validId, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/lentes")
                        .param("id", String.valueOf(validId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()));

        verify(lenteService).search(validId, null, null, null, null, null);
    }

    @Test
    void searchLente_WhenEmpty_ShouldReturn200AndEmptyList() throws Exception {

        when(lenteService.search(null, null, null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/lentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(lenteService).search(null, null, null, null, null, null);
    }

    @Test
    void searchLente_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(get("/lentes")
                        .param("id", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateLente_WithValidData_ShouldReturn200() throws Exception {

        when(lenteService.update(eq(validId), any(LenteUpdateDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/lentes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.tipoLente").value(responseDto.tipoLente()));

        verify(lenteService).update(eq(validId), any());
    }

    @Test
    void updateLente_WithInvalidId_ShouldReturn404() throws Exception {

        when(lenteService.update(eq(invalidId), any()))
                .thenThrow(new ResourceNotFoundException("Lente não encontrada"));

        mockMvc.perform(put("/lentes/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());

        verify(lenteService).update(eq(invalidId), any());
    }


    @Test
    void updateLente_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/lentes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(lenteService, never()).update(any(), any());
    }

    @Test
    void updateLente_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/lentes/{id}", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteLente_WithValidId_ShouldReturn204() throws Exception {

        doNothing().when(lenteService).delete(validId);

        mockMvc.perform(delete("/lentes/{id}", validId))
                .andExpect(status().isNoContent());

        verify(lenteService).delete(validId);
    }

    @Test
    void deleteLente_WithInvalidId_ShouldReturn404() throws Exception {

        doThrow(new ResourceNotFoundException("Lente não encontrada"))
                .when(lenteService).delete(invalidId);

        mockMvc.perform(delete("/lentes/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(lenteService).delete(invalidId);
    }

    @Test
    void deleteLente_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(delete("/lentes/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }
}