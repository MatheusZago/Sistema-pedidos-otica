package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.PedidoResponseDto;
import com.matheusluizago.backend.mapper.PedidoMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.PedidoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final LaboratorioRepository labRepository;
    private final PedidoMapper mapper;

    public PedidoService(PedidoRepository repository,
                         ClienteRepository clienteRepository,
                         LaboratorioRepository labRepository,
                         PedidoMapper mapper){
        this.clienteRepository = clienteRepository;
        this.labRepository = labRepository;
        this.repository = repository;
        this.mapper = mapper;
    }

    public Pedido save(PedidoRegisterDto dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Laboratorio lab = labRepository.findById(dto.laboratorioId())
                .orElseThrow(() -> new RuntimeException("Laboratório não encontrado"));

        Pedido pedidoSalvo = mapper.toEntity(dto, cliente, lab);

        return repository.save(pedidoSalvo);
    }

    public List<PedidoResponseDto> searchByExample(Integer id, Integer clienteId, Integer labId, BigDecimal custo,
                                                   String armacao, BigDecimal od, BigDecimal oe, BigDecimal ad,
                                                   BigDecimal dnp, String tratamento, String tipoLente) {

        Cliente cliente = null;
        if (clienteId != null) {
            cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        }

        Laboratorio lab = null;
        if (labId != null) {
            lab = labRepository.findById(labId)
                    .orElseThrow(() -> new RuntimeException("Laboratório não encontrado"));
        }


        var pedido = new Pedido();
        pedido.setId(id);
        pedido.setCliente(cliente);
        pedido.setLaboratorio(lab);
        pedido.setCliente(cliente);
        pedido.setArmacao(armacao);
        pedido.setOd(od);
        pedido.setOe(oe);
        pedido.setAd(ad);
        pedido.setDnp(dnp);
        pedido.setTratamento(tratamento);
        pedido.setTipoLente(tipoLente);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Pedido> pedidoExample = Example.of(pedido, matcher);

        return repository.findAll(pedidoExample)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
