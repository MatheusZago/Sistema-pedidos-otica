//package com.matheusluizago.backend.integration;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioRegisterDto;
//import com.matheusluizago.backend.dto.laboratorioDto.LaboratorioUpdateDto;
//import com.matheusluizago.backend.model.Laboratorio;
//import com.matheusluizago.backend.repository.LaboratorioRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class LaboratorioControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private LaboratorioRepository laboratorioRepository;
//
//    @BeforeEach
//    void setUp() {
//        laboratorioRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("Deve salvar um laboratório com sucesso")
//    void save_deveSalvarLaboratorioComSucesso() throws Exception {
//        LaboratorioRegisterDto dto = new LaboratorioRegisterDto(
//                "Laboratório Vision",
//                "11999999999",
//                "vision@email.com"
//        );
//
//        mockMvc.perform(post("/laboratorios")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.nome").value("Laboratório Vision"))
//                .andExpect(jsonPath("$.telefone").value("11999999999"))
//                .andExpect(jsonPath("$.email").value("vision@email.com"));
//
//        List<Laboratorio> laboratorios = laboratorioRepository.findAll();
//        assertThat(laboratorios).hasSize(1);
//        assertThat(laboratorios.get(0).getNome()).isEqualTo("Laboratório Vision");
//        assertThat(laboratorios.get(0).getTelefone()).isEqualTo("11999999999");
//        assertThat(laboratorios.get(0).getEmail()).isEqualTo("vision@email.com");
//    }
//
//    @Test
//    @DisplayName("Não deve salvar laboratório com email duplicado")
//    void save_naoDeveSalvarLaboratorioComEmailDuplicado() throws Exception {
//        Laboratorio laboratorio = new Laboratorio();
//        laboratorio.setNome("Laboratório Base");
//        laboratorio.setTelefone("11888888888");
//        laboratorio.setEmail("duplicado@email.com");
//        laboratorioRepository.save(laboratorio);
//
//        LaboratorioRegisterDto dto = new LaboratorioRegisterDto(
//                "Novo Laboratório",
//                "11777777777",
//                "duplicado@email.com"
//        );
//
//        mockMvc.perform(post("/laboratorios")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isConflict());
//
//        List<Laboratorio> laboratorios = laboratorioRepository.findAll();
//        assertThat(laboratorios).hasSize(1);
//    }
//
//    @Test
//    @DisplayName("Deve buscar todos os laboratórios")
//    void search_deveBuscarTodosOsLaboratorios() throws Exception {
//        Laboratorio laboratorio1 = new Laboratorio();
//        laboratorio1.setNome("Lab Alpha");
//        laboratorio1.setTelefone("11111111111");
//        laboratorio1.setEmail("alpha@email.com");
//
//        Laboratorio laboratorio2 = new Laboratorio();
//        laboratorio2.setNome("Lab Beta");
//        laboratorio2.setTelefone("22222222222");
//        laboratorio2.setEmail("beta@email.com");
//
//        laboratorioRepository.saveAll(List.of(laboratorio1, laboratorio2));
//
//        mockMvc.perform(get("/laboratorios")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").isNumber())
//                .andExpect(jsonPath("$[0].nome").isNotEmpty())
//                .andExpect(jsonPath("$[1].id").isNumber())
//                .andExpect(jsonPath("$[1].nome").isNotEmpty());
//    }
//
//    @Test
//    @DisplayName("Deve buscar laboratório por nome")
//    void search_deveBuscarLaboratorioPorNome() throws Exception {
//        Laboratorio laboratorio1 = new Laboratorio();
//        laboratorio1.setNome("Lab Especial");
//        laboratorio1.setTelefone("11111111111");
//        laboratorio1.setEmail("especial@email.com");
//
//        Laboratorio laboratorio2 = new Laboratorio();
//        laboratorio2.setNome("Outro Lab");
//        laboratorio2.setTelefone("22222222222");
//        laboratorio2.setEmail("outro@email.com");
//
//        laboratorioRepository.saveAll(List.of(laboratorio1, laboratorio2));
//
//        mockMvc.perform(get("/laboratorios")
//                        .param("nome", "Especial")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].nome").value("Lab Especial"));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar laboratório com sucesso")
//    void update_deveAtualizarLaboratorioComSucesso() throws Exception {
//        Laboratorio laboratorio = new Laboratorio();
//        laboratorio.setNome("Lab Antigo");
//        laboratorio.setTelefone("11333333333");
//        laboratorio.setEmail("antigo@email.com");
//        laboratorio = laboratorioRepository.save(laboratorio);
//
//        LaboratorioUpdateDto dto = new LaboratorioUpdateDto(
//                "Lab Novo",
//                "11444444444",
//                "novo@email.com"
//        );
//
//        mockMvc.perform(put("/laboratorios/{id}", laboratorio.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(laboratorio.getId()))
//                .andExpect(jsonPath("$.nome").value("Lab Novo"))
//                .andExpect(jsonPath("$.telefone").value("11444444444"))
//                .andExpect(jsonPath("$.email").value("novo@email.com"));
//
//        Laboratorio laboratorioAtualizado = laboratorioRepository.findById(laboratorio.getId()).orElseThrow();
//        assertThat(laboratorioAtualizado.getNome()).isEqualTo("Lab Novo");
//        assertThat(laboratorioAtualizado.getTelefone()).isEqualTo("11444444444");
//        assertThat(laboratorioAtualizado.getEmail()).isEqualTo("novo@email.com");
//    }
//
//    @Test
//    @DisplayName("Não deve atualizar laboratório inexistente")
//    void update_naoDeveAtualizarLaboratorioInexistente() throws Exception {
//        LaboratorioUpdateDto dto = new LaboratorioUpdateDto(
//                "Lab Novo",
//                "11444444444",
//                "novo@email.com"
//        );
//
//        mockMvc.perform(put("/laboratorios/{id}", 9999)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("Deve deletar laboratório com sucesso")
//    void delete_deveDeletarLaboratorioComSucesso() throws Exception {
//        Laboratorio laboratorio = new Laboratorio();
//        laboratorio.setNome("Lab Delete");
//        laboratorio.setTelefone("11555555555");
//        laboratorio.setEmail("delete@email.com");
//        laboratorio = laboratorioRepository.save(laboratorio);
//
//        mockMvc.perform(delete("/laboratorios/{id}", laboratorio.getId()))
//                .andExpect(status().isNoContent());
//
//        assertThat(laboratorioRepository.findById(laboratorio.getId())).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Não deve deletar laboratório inexistente")
//    void delete_naoDeveDeletarLaboratorioInexistente() throws Exception {
//        mockMvc.perform(delete("/laboratorios/{id}", 9999))
//                .andExpect(status().isNotFound());
//    }
//}