package com.matheusluizago.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.factory.PedidoFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.repository.PedidoRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PedidoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private LenteRepository lenteRepository;

    private Cliente validCliente;
    private Laboratorio validLaboratorio;
    private Lente validLente;

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        laboratorioRepository.deleteAll();
        lenteRepository.deleteAll();

        validCliente = ClienteFactory.createValidClienteWithoutId();
        validCliente = clienteRepository.save(validCliente);

        validLaboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        validLaboratorio.setEmail("laboratorio1@email.com");
        validLaboratorio.setCnpj("11222333000181");
        validLaboratorio = laboratorioRepository.save(validLaboratorio);

        validLente = LenteFactory.createValidLenteWithoutId();
        validLente = lenteRepository.save(validLente);
    }

    @Test
    @DisplayName("Deve salvar um pedido com sucesso")
    void save_deveSalvarPedidoComSucesso() throws Exception {
        PedidoRegisterDto baseDto = PedidoFactory.createValidPedidoRegisterDto();

        PedidoRegisterDto dto = new PedidoRegisterDto(
                validCliente.getId(),
                validLaboratorio.getId(),
                validLente.getId(),
                baseDto.armacao(),
                baseDto.armacaoImg(),
                baseDto.odPerto(),
                baseDto.odLonge(),
                baseDto.oePerto(),
                baseDto.oeLonge(),
                baseDto.ad(),
                baseDto.dnp(),
                baseDto.dataEntrega()
        );

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.cliente").value(validCliente.getId()))
                .andExpect(jsonPath("$.laboratorio").value(validLaboratorio.getId()))
                .andExpect(jsonPath("$.lente").value(validLente.getId()))
                .andExpect(jsonPath("$.armacao").value(dto.armacao()))
                .andExpect(jsonPath("$.odPerto").value(dto.odPerto().doubleValue()))
                .andExpect(jsonPath("$.odLonge").value(dto.odLonge().doubleValue()))
                .andExpect(jsonPath("$.oePerto").value(dto.oePerto().doubleValue()))
                .andExpect(jsonPath("$.oeLonge").value(dto.oeLonge().doubleValue()))
                .andExpect(jsonPath("$.ad").value(dto.ad().doubleValue()))
                .andExpect(jsonPath("$.dnp").value(dto.dnp().doubleValue()));

        List<Pedido> pedidos = pedidoRepository.findAll();
        assertThat(pedidos).hasSize(1);
    }

    @Test
    @DisplayName("Deve buscar todos os pedidos")
    void search_deveBuscarTodosOsPedidos() throws Exception {
        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(validCliente);
        pedido1.setLaboratorio(validLaboratorio);
        pedido1.setLente(validLente);

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(validCliente);
        pedido2.setLaboratorio(validLaboratorio);
        pedido2.setLente(validLente);
        pedido2.setArmacao("Armação B");

        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        mockMvc.perform(get("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[1].id").isNumber());
    }

    @Test
    @DisplayName("Deve buscar pedido por armação")
    void search_deveBuscarPedidoPorArmacao() throws Exception {
        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(validCliente);
        pedido1.setLaboratorio(validLaboratorio);
        pedido1.setLente(validLente);
        pedido1.setArmacao("Armação Especial");

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(validCliente);
        pedido2.setLaboratorio(validLaboratorio);
        pedido2.setLente(validLente);
        pedido2.setArmacao("Armação Comum");

        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        mockMvc.perform(get("/pedidos")
                        .param("armacao", "Especial")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].armacao").value("Armação Especial"));
    }

    @Test
    @DisplayName("Deve buscar pedido por nome do laboratório")
    void search_deveBuscarPedidoPorNomeLaboratorio() throws Exception {
        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório B");
        laboratorio2.setEmail("laboratorio2@email.com");
        laboratorio2.setCnpj("12345678000195");
        laboratorio2 = laboratorioRepository.save(laboratorio2);

        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(validCliente);
        pedido1.setLaboratorio(validLaboratorio);
        pedido1.setLente(validLente);

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(validCliente);
        pedido2.setLaboratorio(laboratorio2);
        pedido2.setLente(validLente);

        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        mockMvc.perform(get("/pedidos")
                        .param("laboratorioNome", validLaboratorio.getNome())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].laboratorioNome").value(validLaboratorio.getNome()));
    }

    @Test
    @DisplayName("Deve atualizar pedido com sucesso")
    void update_deveAtualizarPedidoComSucesso() throws Exception {
        Cliente cliente2 = ClienteFactory.createValidClienteWithoutId();
        cliente2.setEmail("cliente2@email.com");
        cliente2.setTelefone("11988888888");
        cliente2 = clienteRepository.save(cliente2);

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório Novo");
        laboratorio2.setEmail("laboratorionovo@email.com");
        laboratorio2.setCnpj("19131243000197");
        laboratorio2 = laboratorioRepository.save(laboratorio2);

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B");
        lente2 = lenteRepository.save(lente2);

        Pedido pedido = PedidoFactory.createValidPedidoWithoutId();
        pedido.setCliente(validCliente);
        pedido.setLaboratorio(validLaboratorio);
        pedido.setLente(validLente);
        pedido = pedidoRepository.save(pedido);

        PedidoUpdateDto baseDto = PedidoFactory.createValidPedidoUpdateDto();

        PedidoUpdateDto dto = new PedidoUpdateDto(
                cliente2.getId(),
                laboratorio2.getId(),
                lente2.getId(),
                baseDto.armacao(),
                baseDto.armacaoImg(),
                baseDto.odPerto(),
                baseDto.odLonge(),
                baseDto.oePerto(),
                baseDto.oeLonge(),
                baseDto.ad(),
                baseDto.dnp(),
                baseDto.dataEntrega()
        );

        mockMvc.perform(put("/pedidos/{id}", pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedido.getId()))
                .andExpect(jsonPath("$.cliente").value(cliente2.getId()))
                .andExpect(jsonPath("$.laboratorio").value(laboratorio2.getId()))
                .andExpect(jsonPath("$.lente").value(lente2.getId()))
                .andExpect(jsonPath("$.armacao").value(dto.armacao()));

        Pedido atualizado = pedidoRepository.findById(pedido.getId()).orElseThrow();
        assertThat(atualizado.getCliente().getId()).isEqualTo(cliente2.getId());
        assertThat(atualizado.getLaboratorio().getId()).isEqualTo(laboratorio2.getId());
        assertThat(atualizado.getLente().getId()).isEqualTo(lente2.getId());
        assertThat(atualizado.getArmacao()).isEqualTo(dto.armacao());
    }

    @Test
    @DisplayName("Não deve atualizar pedido inexistente")
    void update_naoDeveAtualizarPedidoInexistente() throws Exception {
        PedidoUpdateDto dto = PedidoFactory.createValidPedidoUpdateDto();

        mockMvc.perform(put("/pedidos/{id}", 9999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar pedido com sucesso")
    void delete_deveDeletarPedidoComSucesso() throws Exception {
        Pedido pedido = PedidoFactory.createValidPedidoWithoutId();
        pedido.setCliente(validCliente);
        pedido.setLaboratorio(validLaboratorio);
        pedido.setLente(validLente);
        pedido = pedidoRepository.save(pedido);

        mockMvc.perform(delete("/pedidos/{id}", pedido.getId()))
                .andExpect(status().isNoContent());

        assertThat(pedidoRepository.findById(pedido.getId())).isEmpty();
    }

    @Test
    @DisplayName("Não deve deletar pedido inexistente")
    void delete_naoDeveDeletarPedidoInexistente() throws Exception {
        mockMvc.perform(delete("/pedidos/{id}", 9999))
                .andExpect(status().isNotFound());
    }
}