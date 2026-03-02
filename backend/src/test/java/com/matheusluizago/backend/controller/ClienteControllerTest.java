package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteResponseDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

}
