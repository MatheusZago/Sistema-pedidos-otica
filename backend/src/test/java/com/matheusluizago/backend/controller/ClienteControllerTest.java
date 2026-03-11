package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.exceptions.GlobalExceptionHandler;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
@Import(GlobalExceptionHandler.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRegisterDto registerDto;
    private ClienteRegisterDto invalidRegisterDto;
    private ClienteRegisterDto invalidEmailRegisterDto;
    private ClienteResponseDto responseDto;
    private ClienteUpdateDto updateDto;
    private ClienteUpdateDto invalidelefoneUpdateDto;
    private ClienteUpdateDto invalidEmailUpdateDto;

    private final Integer validId = 1;
    private final Integer invalidId = 123123123;

    @BeforeEach
    void setUp(){
        cliente = ClienteFactory.createValidCliente();
        registerDto = ClienteFactory.createValidRegisterClienteDto();
        invalidRegisterDto = ClienteFactory.createInvalidNomeClienteRegister();
        invalidEmailRegisterDto = ClienteFactory.createInvalidEmailClienteRegisterDto();
        responseDto = ClienteFactory.createValidClienteResponseDto();
        updateDto = ClienteFactory.createValidClienteUpdateDto();
        invalidelefoneUpdateDto = ClienteFactory.createInvalidTelefoneClienteUpdateDto();
        invalidEmailUpdateDto = ClienteFactory.createInvalidEmailClienteUpdateDto();

    }



    @Test
    void saveCliente_WithValidData_ShouldReturn201() throws Exception {

        when(clienteService.save(any(ClienteRegisterDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()))
                .andExpect(jsonPath("$.email").value(responseDto.email()));

        verify(clienteService).save(any(ClienteRegisterDto.class));
    }

    @Test
    void saveCliente_WithInvalidNome_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).save(any(ClienteRegisterDto.class));
    }

    @Test
    void saveCliente_WithInvalidEmail_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).save(any(ClienteRegisterDto.class));
    }

    @Test
    void saveCliente_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).save(any(ClienteRegisterDto.class));
    }

    @Test
    void saveCliente_WithEmptyJson_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).save(any(ClienteRegisterDto.class));
    }

    @Test
    void searchCliente_WithoutParams_ShouldReturn200AndList() throws Exception {
        List<ClienteResponseDto> responseList = List.of(responseDto);

        when(clienteService.search(null, null, null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()))
                .andExpect(jsonPath("$[0].email").value(responseDto.email()));

        verify(clienteService).search(null, null, null, null);
    }

    @Test
    void searchCliente_WithIdParam_ShouldReturn200() throws Exception {
        List<ClienteResponseDto> responseList = List.of(responseDto);

        when(clienteService.search(validId, null, null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/clientes")
                        .param("id", String.valueOf(validId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()));

        verify(clienteService).search(validId, null, null, null);
    }

    @Test
    void searchCliente_WithNomeParam_ShouldReturn200() throws Exception {
        List<ClienteResponseDto> responseList = List.of(responseDto);

        when(clienteService.search(null, responseDto.nome(), null, null))
                .thenReturn(responseList);

        mockMvc.perform(get("/clientes")
                        .param("nome", responseDto.nome()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()));

        verify(clienteService).search(null, responseDto.nome(), null, null);
    }

    @Test
    void searchCliente_WithEmailParam_ShouldReturn200() throws Exception {
        List<ClienteResponseDto> responseList = List.of(responseDto);

        when(clienteService.search(null, null, null, responseDto.email()))
                .thenReturn(responseList);

        mockMvc.perform(get("/clientes")
                        .param("email", responseDto.email()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(responseDto.email()));

        verify(clienteService).search(null, null, null, responseDto.email());
    }

    @Test
    void searchCliente_WithAllParams_ShouldReturn200AndFilteredList() throws Exception {
        List<ClienteResponseDto> responseList = List.of(responseDto);

        when(clienteService.search(validId, cliente.getNome(), cliente.getTelefone(), cliente.getEmail()))
                .thenReturn(responseList);

        mockMvc.perform(get("/clientes")
                        .param("id", String.valueOf(validId))
                        .param("nome", cliente.getNome())
                        .param("telefone", cliente.getTelefone())
                        .param("email", cliente.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()))
                .andExpect(jsonPath("$[0].email").value(responseDto.email()));

        verify(clienteService).search(validId, cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
    }

    @Test
    void searchCliente_WhenNoResults_ShouldReturn200AndEmptyList() throws Exception {
        when(clienteService.search(null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(clienteService).search(null, null, null, null);
    }

    @Test
    void updateCliente_WithValidData_ShouldReturn200() throws Exception {

        when(clienteService.update(validId, updateDto))
                .thenReturn(responseDto);

        mockMvc.perform(put("/clientes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()))
                .andExpect(jsonPath("$.email").value(responseDto.email()));

        verify(clienteService).update(validId, updateDto);
    }

    @Test
    void updateCliente_WithInvalidId_ShouldReturn404() throws Exception {

        when(clienteService.update(invalidId, updateDto))
                .thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        mockMvc.perform(put("/clientes/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCliente_WithInvalidTelefone_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/clientes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidelefoneUpdateDto)))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).update(any(), any());
    }

    @Test
    void updateCliente_WithInvalidEmail_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/clientes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailUpdateDto)))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).update(any(), any());
    }

    @Test
    void updateCliente_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/clientes/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).update(any(), any());
    }

    @Test
    void deleteCliente_WithValidId_ShouldReturn204() throws Exception {

        doNothing().when(clienteService).delete(validId);

        mockMvc.perform(delete("/clientes/{id}", validId))
                .andExpect(status().isNoContent());

        verify(clienteService).delete(validId);
    }

    @Test
    void deleteCliente_WithInvalidId_ShouldReturn404() throws Exception {

        doThrow(new ResourceNotFoundException("Cliente não encontrado"))
                .when(clienteService).delete(invalidId);

        mockMvc.perform(delete("/clientes/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(clienteService).delete(invalidId);
    }

    @Test
    void deleteCliente_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(delete("/clientes/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }



}
