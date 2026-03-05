package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService service;

    private Cliente cliente;
    private ClienteRegisterDto registerDto;
    private ClienteRegisterDto invalidRegisterDto;
    private ClienteRegisterDto invalidEmailRegisterDto;
    private ClienteResponseDto responseDto;
    private ClienteUpdateDto updateDto;
    private ClienteUpdateDto invalidUpdateDto;
    private ClienteUpdateDto invalidEmailUpdateDto;

    private Integer validId = 1;
    private Integer invalidId = 123123123;

    @BeforeEach
    void setUp(){
        cliente = ClienteFactory.createValidCliente();
        registerDto = ClienteFactory.createValidRegisterClienteDto();
        invalidRegisterDto = ClienteFactory.createInvalidNomeClienteRegister();
        invalidEmailRegisterDto = ClienteFactory.createInvalidEmailClienteRegisterDto();
        responseDto = ClienteFactory.createValidClienteResponseDto();
        updateDto = ClienteFactory.createValidClienteUpdateDto();
        invalidUpdateDto = ClienteFactory.createInvalidTelefoneClienteUpdateDto();
        invalidEmailUpdateDto = ClienteFactory.createInvalidEmailClienteUpdateDto();

    }



    @Test
    void saveCliente_WithValidData_ShouldReturn201() throws Exception {

        when(service.save(any(ClienteRegisterDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.nome").value(responseDto.nome()))
                .andExpect(jsonPath("$.email").value(responseDto.email()));

        verify(service).save(any(ClienteRegisterDto.class));
    }

}
