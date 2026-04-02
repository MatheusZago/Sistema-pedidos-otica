package com.matheusluizago.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.dto.clienteDto.ClienteUpdateDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void shouldCreateCliente() throws Exception {
        ClienteRegisterDto dto = ClienteFactory.createValidRegisterClienteDto();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Matheus"))
                .andExpect(jsonPath("$.email").value("matheus@email.com"))
                .andExpect(jsonPath("$.telefone").value("11912345678"));

        Optional<Cliente> result = clienteRepository.findByEmail("matheus@email.com");

        System.out.println(objectMapper.writeValueAsString(dto));

        assertTrue(result.isPresent());
        assertEquals("Matheus", result.get().getNome());
    }

    @Test
    void shouldReturnBadRequestWhenCreateClienteWithInvalidEmail() throws Exception {
        ClienteRegisterDto dto = ClienteFactory.createInvalidEmailClienteRegisterDto();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFindClienteByEmail() throws Exception {
        Cliente cliente = ClienteFactory.createValidClienteWithoutId();
        clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes")
                        .param("email", "matheus@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Matheus"))
                .andExpect(jsonPath("$[0].email").value("matheus@email.com"));
    }

    @Test
    void shouldUpdateCliente() throws Exception {
        Cliente cliente = ClienteFactory.createValidClienteWithoutId();
        Cliente savedCliente = clienteRepository.save(cliente);

        ClienteUpdateDto dto = ClienteFactory.createValidClienteUpdateDto();

        mockMvc.perform(put("/clientes/{id}", savedCliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matheus"))
                .andExpect(jsonPath("$.email").value("matheus@email.com"));

        Optional<Cliente> updatedCliente = clienteRepository.findById(savedCliente.getId());

        assertTrue(updatedCliente.isPresent());
        assertEquals("Matheus", updatedCliente.get().getNome());
    }

    @Test
    void shouldDeleteCliente() throws Exception {
        Cliente cliente = ClienteFactory.createValidClienteWithoutId();
        Cliente savedCliente = clienteRepository.save(cliente);

        mockMvc.perform(delete("/clientes/{id}", savedCliente.getId()))
                .andExpect(status().isNoContent());

        Optional<Cliente> result = clienteRepository.findById(savedCliente.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnNotFoundWhenDeleteClienteWithInvalidId() throws Exception {
        mockMvc.perform(delete("/clientes/{id}", 999))
                .andExpect(status().isNotFound());
    }
}