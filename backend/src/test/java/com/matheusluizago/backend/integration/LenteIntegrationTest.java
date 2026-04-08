package com.matheusluizago.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.LenteRepository;
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
class LenteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LenteRepository lenteRepository;

    @BeforeEach
    void setUp() {
        lenteRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar uma lente com sucesso")
    void save_deveSalvarLenteComSucesso() throws Exception {

        LenteRegisterDto dto = LenteFactory.createValidLenteRegisterDto();

        mockMvc.perform(post("/lentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.tipoLente").value(dto.tipoLente()))
                .andExpect(jsonPath("$.custo").value(dto.custo().doubleValue()))
                .andExpect(jsonPath("$.tratamento").value(dto.tratamento()))
                .andExpect(jsonPath("$.indice").value(dto.indice()))
                .andExpect(jsonPath("$.valorVenda").value(dto.valorVenda().doubleValue()));

        List<Lente> lentes = lenteRepository.findAll();
        assertThat(lentes).hasSize(1);
    }

    @Test
    @DisplayName("Deve buscar todas as lentes")
    void search_deveBuscarTodasAsLentes() throws Exception {

        Lente lente1 = LenteFactory.createValidLenteWithoutId();

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B"); // evita duplicidade de dados iguais

        lenteRepository.saveAll(List.of(lente1, lente2));

        mockMvc.perform(get("/lentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[1].id").isNumber());
    }

    @Test
    @DisplayName("Deve buscar lente por tipo")
    void search_deveBuscarLentePorTipo() throws Exception {

        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Visão Simples");

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Multifocal");

        lenteRepository.saveAll(List.of(lente1, lente2));

        mockMvc.perform(get("/lentes")
                        .param("tipoLente", "Visão"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoLente").value("Visão Simples"));
    }

    @Test
    @DisplayName("Deve atualizar lente com sucesso")
    void update_deveAtualizarLenteComSucesso() throws Exception {

        Lente lente = LenteFactory.createValidLenteWithoutId();
        lente = lenteRepository.save(lente);

        LenteUpdateDto dto = LenteFactory.createValidLenteUpdateDto();

        mockMvc.perform(put("/lentes/{id}", lente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lente.getId()))
                .andExpect(jsonPath("$.tipoLente").value(dto.tipoLente()))
                .andExpect(jsonPath("$.custo").value(dto.custo().doubleValue()))
                .andExpect(jsonPath("$.tratamento").value(dto.tratamento()))
                .andExpect(jsonPath("$.indice").value(dto.indice()))
                .andExpect(jsonPath("$.valorVenda").value(dto.valorVenda().doubleValue()));

        Lente atualizado = lenteRepository.findById(lente.getId()).orElseThrow();
        assertThat(atualizado.getTipoLente()).isEqualTo(dto.tipoLente());
    }

    @Test
    @DisplayName("Não deve atualizar lente inexistente")
    void update_naoDeveAtualizarLenteInexistente() throws Exception {

        LenteUpdateDto dto = LenteFactory.createValidLenteUpdateDto();

        mockMvc.perform(put("/lentes/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar lente com sucesso")
    void delete_deveDeletarLenteComSucesso() throws Exception {

        Lente lente = LenteFactory.createValidLenteWithoutId();
        lente = lenteRepository.save(lente);

        mockMvc.perform(delete("/lentes/{id}", lente.getId()))
                .andExpect(status().isNoContent());

        assertThat(lenteRepository.findById(lente.getId())).isEmpty();
    }

    @Test
    @DisplayName("Não deve deletar lente inexistente")
    void delete_naoDeveDeletarLenteInexistente() throws Exception {

        mockMvc.perform(delete("/lentes/{id}", 9999))
                .andExpect(status().isNotFound());
    }
}