package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.factory.PedidoFactory;
import com.matheusluizago.backend.mapper.PedidoMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private LaboratorioRepository labRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private LenteRepository lenteRepository;

    @Mock
    private PedidoMapper mapper;

    @InjectMocks
    private PedidoService service;

    private Pedido pedido;
    private PedidoRegisterDto registerDto;
    private PedidoResponseDto responseDto;
    private PedidoUpdateDto updateDto;
    private final Integer validId = 1;
    private final Integer invalidId = 123123123;

    private Lente lente;
    private Cliente cliente;
    private Laboratorio lab;

    @BeforeEach
    void setUp(){
        pedido = PedidoFactory.createValidPedido();
        registerDto = PedidoFactory.createValidPedidoRegisterDto();
        responseDto = PedidoFactory.createValidPedidoResponseDto();
        updateDto = PedidoFactory.createValidPedidoUpdateDto();

        lente = LenteFactory.createValidLente();
        cliente = ClienteFactory.createValidCliente();
        lab = LaboratorioFactory.createValidLaboratorio();
    }


    @Test
    void savePedido_WithValidData_ShouldSave(){

        //TODO fazer os testes de chamar os outros

        //Mockand comportamento
        when(clienteRepository.findById(validId))
                .thenReturn(Optional.of(cliente));

        when(labRepository.findById(validId))
                .thenReturn(Optional.of(lab));

        when(lenteRepository.findById(validId))
                .thenReturn(Optional.of(lente));

        when(mapper.toEntity(registerDto, cliente, lab, lente))
                .thenReturn(pedido);

        when(repository.save(pedido))
                .thenReturn(pedido);

        when(mapper.toDto(pedido))
                .thenReturn(responseDto);

        PedidoResponseDto test = service.save(registerDto);

        assertNotNull(test);

        //Está vendo se esses métodos foram chamados
        verify(mapper).toEntity(registerDto, cliente, lab, lente);
        verify(repository).save(pedido);
        verify(mapper).toDto(pedido);
    }

    @Test
    void searchPedido_WithFilters_ShouldReturnList() {

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(pedido));
        when(mapper.toDto(pedido)).thenReturn(responseDto);

        List<PedidoResponseDto> test = service.search(
                1,
                1,
                "Cliente A",
                "email@email.com",
                "11987654321",
                1,
                "Laboratório A",
                "Endereço A",
                "123456789",
                1,
                BigDecimal.valueOf(50),
                "Tratamento A",
                "Indice A",
                "Lente A",
                BigDecimal.valueOf(80),
                "Armacao A",
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1)
        );

        assertNotNull(test);
        assertEquals(1, test.size());
        assertEquals(responseDto, test.get(0));

        verify(repository).findAll(any(Specification.class));
        verify(mapper).toDto(pedido);
    }


    @Test
    void searchPedido_WhenFilterMatchesNothing_ShouldReturnEmptyList() {

        when(repository.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<PedidoResponseDto> test = service.search(
                null,
                null,
                "Cliente Inexistente",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertNotNull(test);
        assertTrue(test.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updatePedido_WithValiData_ShouldReturnUpdatedClient(){

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);


        when(repository.findById(validId)).thenReturn(Optional.of(pedido));
        when(repository.save(pedido)).thenReturn(pedido);
        when(mapper.toDto(pedido)).thenReturn(responseDto);

        PedidoResponseDto test = service.update(validId, updateDto);

        verify(repository).findById(validId);
        verify(mapper).updatePedido(
                eq(pedido),
                eq(updateDto),
                clienteCaptor.capture(),
                any(Laboratorio.class),
                any(Lente.class)
        );
        verify(repository).save(pedido);
        verify(mapper).toDto(pedido);
        assertNotNull(test);
        assertEquals(responseDto, test);
        assertEquals(1, clienteCaptor.getValue().getId());
    }

    @Test
    void updatePedido_WhenIdNotFound_ShouldThroewException(){

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(invalidId, PedidoFactory.createValidPedidoUpdateDto())
        );

        verify(repository).findById(invalidId);
        verify(repository, never()).save(any());

    }

    @Test
    void deletePedido_WithValidId_ShouldDelete(){

        when(repository.findById(validId)).thenReturn(Optional.of(pedido));

        service.delete(validId);

        verify(repository).findById(validId);
        verify(repository).delete(pedido);
    }

    @Test
    void deletePedido_WithInvalidId_ShouldThrowException(){

        when(repository.findById(validId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.delete(validId)
        );

        verify(repository).findById(validId);
        verify(repository, never()).delete(any(Pedido.class));
    }

}
