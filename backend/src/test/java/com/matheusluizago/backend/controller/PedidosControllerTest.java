package com.matheusluizago.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
import com.matheusluizago.backend.exceptions.DuplicateRegisterException;
import com.matheusluizago.backend.exceptions.GlobalExceptionHandler;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.PedidoFactory;
import com.matheusluizago.backend.service.PedidoService;
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


@WebMvcTest(PedidoController.class)
@Import(GlobalExceptionHandler.class)
public class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoService pedidoService;

    private PedidoRegisterDto registerDto;
    private PedidoRegisterDto registerDtoWithoutOptionalFields;

    private PedidoRegisterDto invalidClienteIdRegisterDto;
    private PedidoRegisterDto invalidLabIdRegisterDto;
    private PedidoRegisterDto invalidLenteIdRegisterDto;
    private PedidoRegisterDto invalidArmacaoRegisterDto;
    private PedidoRegisterDto invalidOdPertoRegisterDto;
    private PedidoRegisterDto invalidOdLongeRegisterDto;
    private PedidoRegisterDto invalidOePertoRegisterDto;
    private PedidoRegisterDto invalidOeLongeRegisterDto;
    private PedidoRegisterDto invalidAdRegisterDto;
    private PedidoRegisterDto invalidDnpRegisterDto;

    private PedidoResponseDto responseDto;

    private PedidoUpdateDto updateDto;
    private PedidoUpdateDto updateDtoWithLessFields;

    private final Integer validId = 1;
    private final Integer invalidId = 123123123;

    @BeforeEach
    void setUp() {
        registerDto = PedidoFactory.createValidPedidoRegisterDto();
        registerDtoWithoutOptionalFields = PedidoFactory.createValidPedidoRegisterDtoWithoutOptionalFields();

        invalidClienteIdRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoClienteId();
        invalidLabIdRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoLabId();
        invalidLenteIdRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoLenteId();
        invalidArmacaoRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoArmacao();
        invalidOdPertoRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoOdPerto();
        invalidOdLongeRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoOdLonge();
        invalidOePertoRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoOePerto();
        invalidOeLongeRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoOeLonge();
        invalidAdRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoAd();
        invalidDnpRegisterDto = PedidoFactory.createInvalidPedidoRegisterDtoDnp();

        responseDto = PedidoFactory.createValidPedidoResponseDto();

        updateDto = PedidoFactory.createValidPedidoUpdateDto();
        updateDtoWithLessFields = PedidoFactory.createValidPedidoUpdateDtoWithLessFields();
    }

    @Test
    void savePedido_WithValidData_ShouldReturn201() throws Exception {

        when(pedidoService.save(any(PedidoRegisterDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.cliente").value(responseDto.cliente()))
                .andExpect(jsonPath("$.clienteNome").value(responseDto.clienteNome()))
                .andExpect(jsonPath("$.laboratorio").value(responseDto.laboratorio()))
                .andExpect(jsonPath("$.lente").value(responseDto.lente()))
                .andExpect(jsonPath("$.armacao").value(responseDto.armacao()));

        verify(pedidoService).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidClienteId_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClienteIdRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidLabId_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidLabIdRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidLenteId_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidLenteIdRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidArmacao_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidArmacaoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidOdPerto_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOdPertoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidOdLonge_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOdLongeRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidOePerto_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOePertoRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidOeLonge_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOeLongeRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidAd_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAdRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithInvalidDnp_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDnpRegisterDto)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithEmptyJson_ShouldReturn400() throws Exception {

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(PedidoRegisterDto.class));
    }

    @Test
    void savePedido_WithDuplicateData_ShouldReturn409() throws Exception {

        when(pedidoService.save(any(PedidoRegisterDto.class)))
                .thenThrow(new DuplicateRegisterException("Pedido já cadastrado."));

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isConflict());

        verify(pedidoService).save(any(PedidoRegisterDto.class));
    }

    @Test
    void searchPedido_WithoutParams_ShouldReturn200AndList() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].cliente").value(responseDto.cliente()))
                .andExpect(jsonPath("$[0].clienteNome").value(responseDto.clienteNome()))
                .andExpect(jsonPath("$[0].laboratorio").value(responseDto.laboratorio()))
                .andExpect(jsonPath("$[0].lente").value(responseDto.lente()))
                .andExpect(jsonPath("$[0].armacao").value(responseDto.armacao()));

        verify(pedidoService).search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithIdParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                validId, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("id", String.valueOf(validId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()));

        verify(pedidoService).search(
                validId, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithClienteIdParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, responseDto.cliente(), null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("clienteId", String.valueOf(responseDto.cliente())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cliente").value(responseDto.cliente()));

        verify(pedidoService).search(
                null, responseDto.cliente(), null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithClienteNomeParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, responseDto.clienteNome(), null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("clienteNome", responseDto.clienteNome()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteNome").value(responseDto.clienteNome()));

        verify(pedidoService).search(
                null, null, responseDto.clienteNome(), null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithLaboratorioIdParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, null, null, null,
                responseDto.laboratorio(), null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("laboratorioId", String.valueOf(responseDto.laboratorio())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].laboratorio").value(responseDto.laboratorio()));

        verify(pedidoService).search(
                null, null, null, null, null,
                responseDto.laboratorio(), null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithLenteIdParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, null, null, null,
                null, null, null, null,
                responseDto.lente(), null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("lenteId", String.valueOf(responseDto.lente())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lente").value(responseDto.lente()));

        verify(pedidoService).search(
                null, null, null, null, null,
                null, null, null, null,
                responseDto.lente(), null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithTipoLenteParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, responseDto.tipoLente(), null,
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("tipoLente", responseDto.tipoLente()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoLente").value(responseDto.tipoLente()));

        verify(pedidoService).search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, responseDto.tipoLente(), null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithValorVendaParam_ShouldReturn200() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, responseDto.valorVenda(),
                null, null, null, null, null, null, null))
                .thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("valorVenda", responseDto.valorVenda().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valorVenda").value(responseDto.valorVenda().doubleValue()));

        verify(pedidoService).search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, responseDto.valorVenda(),
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithAllParams_ShouldReturn200AndFilteredList() throws Exception {

        List<PedidoResponseDto> list = List.of(responseDto);

        when(pedidoService.search(
                validId,
                responseDto.cliente(),
                responseDto.clienteNome(),
                "matheus@email.com",
                "11999999999",
                responseDto.laboratorio(),
                responseDto.laboratorioNome(),
                "Rua A",
                "12345678000199",
                responseDto.lente(),
                responseDto.custo(),
                responseDto.tratamento(),
                responseDto.indice(),
                responseDto.tipoLente(),
                responseDto.valorVenda(),
                responseDto.armacao(),
                responseDto.odPerto(),
                responseDto.odLonge(),
                responseDto.oePerto(),
                responseDto.oeLonge(),
                responseDto.ad(),
                responseDto.dnp()
        )).thenReturn(list);

        mockMvc.perform(get("/pedidos")
                        .param("id", String.valueOf(validId))
                        .param("clienteId", String.valueOf(responseDto.cliente()))
                        .param("clienteNome", responseDto.clienteNome())
                        .param("clienteEmail", "matheus@email.com")
                        .param("clienteTelefone", "11999999999")
                        .param("laboratorioId", String.valueOf(responseDto.laboratorio()))
                        .param("laboratorioNome", responseDto.laboratorioNome())
                        .param("laboratorioEndereco", "Rua A")
                        .param("laboratorioCnpj", "12345678000199")
                        .param("lenteId", String.valueOf(responseDto.lente()))
                        .param("lenteCusto", responseDto.custo().toString())
                        .param("lenteTratamento", responseDto.tratamento())
                        .param("lenteIndice", responseDto.indice())
                        .param("tipoLente", responseDto.tipoLente())
                        .param("valorVenda", responseDto.valorVenda().toString())
                        .param("armacao", responseDto.armacao())
                        .param("odPerto", responseDto.odPerto().toString())
                        .param("odLonge", responseDto.odLonge().toString())
                        .param("oePerto", responseDto.oePerto().toString())
                        .param("oeLonge", responseDto.oeLonge().toString())
                        .param("ad", responseDto.ad().toString())
                        .param("dnp", responseDto.dnp().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.id()))
                .andExpect(jsonPath("$[0].cliente").value(responseDto.cliente()))
                .andExpect(jsonPath("$[0].laboratorio").value(responseDto.laboratorio()))
                .andExpect(jsonPath("$[0].lente").value(responseDto.lente()))
                .andExpect(jsonPath("$[0].armacao").value(responseDto.armacao()));

        verify(pedidoService).search(
                validId,
                responseDto.cliente(),
                responseDto.clienteNome(),
                "matheus@email.com",
                "11999999999",
                responseDto.laboratorio(),
                responseDto.laboratorioNome(),
                "Rua A",
                "12345678000199",
                responseDto.lente(),
                responseDto.custo(),
                responseDto.tratamento(),
                responseDto.indice(),
                responseDto.tipoLente(),
                responseDto.valorVenda(),
                responseDto.armacao(),
                responseDto.odPerto(),
                responseDto.odLonge(),
                responseDto.oePerto(),
                responseDto.oeLonge(),
                responseDto.ad(),
                responseDto.dnp()
        );
    }

    @Test
    void searchPedido_WhenNoResults_ShouldReturn200AndEmptyList() throws Exception {

        when(pedidoService.search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(pedidoService).search(
                null, null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    void searchPedido_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(get("/pedidos")
                        .param("id", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchPedido_WithInvalidValorVendaType_ShouldReturn400() throws Exception {

        mockMvc.perform(get("/pedidos")
                        .param("valorVenda", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchPedido_WithInvalidLenteCustoType_ShouldReturn400() throws Exception {

        mockMvc.perform(get("/pedidos")
                        .param("lenteCusto", "abc"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updatePedido_WithValidData_ShouldReturn200() throws Exception {

        when(pedidoService.update(eq(validId), any(PedidoUpdateDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/pedidos/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.cliente").value(responseDto.cliente()))
                .andExpect(jsonPath("$.clienteNome").value(responseDto.clienteNome()))
                .andExpect(jsonPath("$.laboratorio").value(responseDto.laboratorio()))
                .andExpect(jsonPath("$.lente").value(responseDto.lente()))
                .andExpect(jsonPath("$.armacao").value(responseDto.armacao()));

        verify(pedidoService).update(eq(validId), any(PedidoUpdateDto.class));
    }

    @Test
    void updatePedido_WithInvalidId_ShouldReturn404() throws Exception {

        when(pedidoService.update(eq(invalidId), any(PedidoUpdateDto.class)))
                .thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        mockMvc.perform(put("/pedidos/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());

        verify(pedidoService).update(eq(invalidId), any(PedidoUpdateDto.class));
    }

    @Test
    void updatePedido_WithEmptyBody_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/pedidos/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).update(any(), any());
    }

    @Test
    void updatePedido_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(put("/pedidos/{id}", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePedido_WithValidId_ShouldReturn204() throws Exception {

        doNothing().when(pedidoService).delete(validId);

        mockMvc.perform(delete("/pedidos/{id}", validId))
                .andExpect(status().isNoContent());

        verify(pedidoService).delete(validId);
    }

    @Test
    void deletePedido_WithInvalidId_ShouldReturn404() throws Exception {

        doThrow(new ResourceNotFoundException("Pedido não encontrado"))
                .when(pedidoService).delete(invalidId);

        mockMvc.perform(delete("/pedidos/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(pedidoService).delete(invalidId);
    }

    @Test
    void deletePedido_WithInvalidIdType_ShouldReturn400() throws Exception {

        mockMvc.perform(delete("/pedidos/{id}", "abc"))
                .andExpect(status().isBadRequest());
    }
}
