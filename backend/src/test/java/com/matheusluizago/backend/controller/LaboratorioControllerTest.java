package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioResponseDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.GlobalExceptionHandler;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.service.LaboratorioService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LaboratorioController.class)
@Import(GlobalExceptionHandler.class)
public class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LaboratorioService laboratorioService;

    private LaboratorioRegisterDto registerDto;
    private LaboratorioRegisterDto invalidNomeRegisterDto;
    private LaboratorioRegisterDto invalidEnderecoRegisterDto;
    private LaboratorioRegisterDto invalidCnpjRegisterDto;
    private LaboratorioUpdateDto validUpdateDto;
    private LaboratorioUpdateDto invalidUpdateDtoCnpj;
    private LaboratorioResponseDto responseDto;

    private final Integer validId = 1;
    private final Integer invalidId = 1233123123;

    @BeforeEach
    void setup(){
        registerDto = LaboratorioFactory.createValidLaboratorioRegisterDto();
        invalidNomeRegisterDto = LaboratorioFactory.createInvalidLaboratorioRegisterDtoNome();
        invalidEnderecoRegisterDto = LaboratorioFactory.createInvalidLaboratorioRegisterDtoEndereco();
        invalidCnpjRegisterDto = LaboratorioFactory.createInvalidLaboratorioRegisterDtoCnpj();
        validUpdateDto = LaboratorioFactory.createValidLaboratorioUpdateDto();
        invalidUpdateDtoCnpj = LaboratorioFactory.createInvalidLaboratorioUpdateDtoCnpj();
        responseDto = LaboratorioFactory.createValidLaboratorioResponseDto();
    }

    @Test
    void saveLaboratorio_WithValidData_ShouldReturn201() throws Exception {

        when(laboratorioService.save(any(LaboratorioRegisterDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()));

        verify(laboratorioService).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithInvalidNome_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidNomeRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithInvalidEndereco_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEnderecoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithInvalidCnpj_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCnpjRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithEmptyJson_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void saveLaboratorio_WithDuplicateData_ShouldReturn409() throws Exception {

        when(laboratorioService.save(any(LaboratorioRegisterDto.class)))
                .thenThrow(new DuplicateRegisterException("Laboratório já cadastrado."));

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isConflict());

        verify(laboratorioService).save(any(LaboratorioRegisterDto.class));
    }

    @Test
    void searchLaboratorio_WithoutParams_ShouldReturn200AndList() throws Exception {
        List<LaboratorioResponseDto> responseList = List.of(responseDto);

        when(laboratorioService.search(null, null, null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()));

        verify(laboratorioService).search(null, null, null, null);
    }

    @Test
    void searchLaboratorio_WithIdParam_ShouldReturn200() throws Exception {
        List<LaboratorioResponseDto> responseList = List.of(responseDto);

        when(laboratorioService.search(validId, null, null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/laboratorios")
                        .param("id", String.valueOf(validId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()));

        verify(laboratorioService).search(validId, null, null, null);
    }

    @Test
    void searchLaboratorio_WithNomeParam_ShouldReturn200() throws Exception {
        List<LaboratorioResponseDto> responseList = List.of(responseDto);

        when(laboratorioService.search(null, responseDto.nome(), null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/laboratorios")
                        .param("nome", responseDto.nome()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()));

        verify(laboratorioService).search(null, responseDto.nome(), null, null);
    }

    @Test
    void searchLaboratorio_WithCnpjParam_ShouldReturn200() throws Exception {
        List<LaboratorioResponseDto> responseList = List.of(responseDto);

        when(laboratorioService.search(null, null, null, responseDto.cnpj()))
                .thenReturn(responseList);

        mockMvc.perform(get("/laboratorios")
                        .param("cnpj", responseDto.cnpj()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cnpj").value(responseDto.cnpj()));

        verify(laboratorioService).search(null, null, null, responseDto.cnpj());
    }

    @Test
    void updateLaboratorio_WithValidData_ShouldReturn200() throws Exception {

        when(laboratorioService.update(eq(validId), any(LaboratorioUpdateDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/laboratorios/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()))
                .andExpect(jsonPath("$.endereco").value(responseDto.endereco()))
                .andExpect(jsonPath("$.cnpj").value(responseDto.cnpj()));

        verify(laboratorioService).update(eq(validId), any(LaboratorioUpdateDto.class));
    }

    @Test
    void updateLaboratorio_WithInvalidId_ShouldReturn404() throws Exception {

        when(laboratorioService.update(eq(invalidId), any(LaboratorioUpdateDto.class)))
                .thenThrow(new ResourceNotFoundException("Laboratório não encontrado"));

        mockMvc.perform(put("/laboratorios/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateDto)))
                .andExpect(status().isNotFound());

        verify(laboratorioService).update(eq(invalidId), any(LaboratorioUpdateDto.class));
    }

    @Test
    void updateLaboratorio_WithInvalidCnpj_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/laboratorios/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUpdateDtoCnpj)))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).update(any(), any());
    }

    @Test
    void updateLaboratorio_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/laboratorios/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(laboratorioService, never()).update(any(), any());
    }

    @Test
    void deleteLaboratorio_WithValidId_ShouldReturn204() throws Exception {

        doNothing().when(laboratorioService).delete(validId);

        mockMvc.perform(delete("/laboratorios/{id}", validId))
                .andExpect(status().isNoContent());

        verify(laboratorioService).delete(validId);
    }

    @Test
    void deleteLaboratorio_WithInvalidId_ShouldReturn404() throws Exception {

        doThrow(new ResourceNotFoundException("Laboratório não encontrado"))
                .when(laboratorioService).delete(invalidId);

        mockMvc.perform(delete("/laboratorios/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(laboratorioService).delete(invalidId);
    }

    @Test
    void deleteLaboratorio_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(delete("/laboratorios/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }

}
