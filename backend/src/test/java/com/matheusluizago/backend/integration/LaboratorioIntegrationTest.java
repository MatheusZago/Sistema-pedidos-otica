package com.matheusluizago.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LaboratorioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @BeforeEach
    void setUp() {
        laboratorioRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar um laboratório com sucesso")
    void save_deveSalvarLaboratorioComSucesso() throws Exception {

        LaboratorioRegisterDto dto = LaboratorioFactory.createValidLaboratorioRegisterDto();

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value(dto.nome()))
                .andExpect(jsonPath("$.endereco").value(dto.endereco()))
                .andExpect(jsonPath("$.cnpj").value(dto.cnpj()))
                .andExpect(jsonPath("$.telefone").value(dto.telefone()))
                .andExpect(jsonPath("$.email").value(dto.email()));

        List<Laboratorio> laboratorios = laboratorioRepository.findAll();
        assertThat(laboratorios).hasSize(1);
    }

    @Test
    @DisplayName("Não deve salvar laboratório com email duplicado")
    void save_naoDeveSalvarLaboratorioComEmailDuplicado() throws Exception {

        Laboratorio laboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorioRepository.save(laboratorio);

        // precisa mudar email ou manter igual dependendo do cenário
        LaboratorioRegisterDto dto = LaboratorioFactory.createValidLaboratorioRegisterDto();

        mockMvc.perform(post("/laboratorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());

        assertThat(laboratorioRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Deve buscar todos os laboratórios")
    void search_deveBuscarTodosOsLaboratorios() throws Exception {

        Laboratorio lab1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        lab1.setEmail("lab1@email.com");

        Laboratorio lab2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        lab2.setEmail("lab2@email.com");

        laboratorioRepository.saveAll(List.of(lab1, lab2));

        mockMvc.perform(get("/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[1].id").isNumber());
    }

    @Test
    @DisplayName("Deve buscar laboratório por nome")
    void search_deveBuscarLaboratorioPorNome() throws Exception {

        Laboratorio lab1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        lab1.setNome("Lab Especial");
        lab1.setEmail("especial@email.com");

        Laboratorio lab2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        lab2.setNome("Outro Lab");
        lab2.setEmail("outro@email.com");

        laboratorioRepository.saveAll(List.of(lab1, lab2));

        mockMvc.perform(get("/laboratorios")
                        .param("nome", "Especial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Lab Especial"));
    }

    @Test
    @DisplayName("Deve atualizar laboratório com sucesso")
    void update_deveAtualizarLaboratorioComSucesso() throws Exception {

        Laboratorio laboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio = laboratorioRepository.save(laboratorio);

        LaboratorioUpdateDto dto = LaboratorioFactory.createValidLaboratorioUpdateDto();

        mockMvc.perform(put("/laboratorios/{id}", laboratorio.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(dto.nome()))
                .andExpect(jsonPath("$.email").value(dto.email()));

        Laboratorio atualizado = laboratorioRepository.findById(laboratorio.getId()).orElseThrow();
        assertThat(atualizado.getNome()).isEqualTo(dto.nome());
    }

    @Test
    @DisplayName("Não deve atualizar laboratório inexistente")
    void update_naoDeveAtualizarLaboratorioInexistente() throws Exception {

        LaboratorioUpdateDto dto = LaboratorioFactory.createValidLaboratorioUpdateDto();

        mockMvc.perform(put("/laboratorios/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar laboratório com sucesso")
    void delete_deveDeletarLaboratorioComSucesso() throws Exception {

        Laboratorio laboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio = laboratorioRepository.save(laboratorio);

        mockMvc.perform(delete("/laboratorios/{id}", laboratorio.getId()))
                .andExpect(status().isNoContent());

        assertThat(laboratorioRepository.findById(laboratorio.getId())).isEmpty();
    }

    @Test
    @DisplayName("Não deve deletar laboratório inexistente")
    void delete_naoDeveDeletarLaboratorioInexistente() throws Exception {

        mockMvc.perform(delete("/laboratorios/{id}", 9999))
                .andExpect(status().isNotFound());
    }
}