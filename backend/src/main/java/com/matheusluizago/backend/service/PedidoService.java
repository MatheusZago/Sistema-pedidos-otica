package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.LaboratorioRegisterDto;
import com.matheusluizago.backend.dto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.PedidoResponseDto;
import com.matheusluizago.backend.mapper.PedidoMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.PedidoRepository;
import com.matheusluizago.backend.repository.specs.PedidosSpecs;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<PedidoResponseDto> search(Integer id, Integer clienteId, String clienteNome,
                                          String clienteEmail, String clienteTelefone,
                                          Integer labId, String labNome, String labEndereco,
                                          BigDecimal custo, String armacao,
                                          BigDecimal od, BigDecimal oe, BigDecimal ad,
                                          BigDecimal dnp, String tratamento, String tipoLente){

        Specification<Pedido> spec = Specification
                .where(PedidosSpecs.idEqual(id))
                .and(PedidosSpecs.idClienteEqual(clienteId))
                .and(PedidosSpecs.nomeClienteLike(clienteNome))
                .and(PedidosSpecs.emailClienteLike(clienteEmail))
                .and(PedidosSpecs.telefoneClienteLike(clienteTelefone))
                .and(PedidosSpecs.idLaboratorioEqual(labId))
                .and(PedidosSpecs.nomeLaboratorioLike(labNome))
                .and(PedidosSpecs.enderecoLaboratorioLike(labEndereco))
                .and(PedidosSpecs.custoEqual(custo))
                .and(PedidosSpecs.armacaoLike(armacao))
                .and(PedidosSpecs.odEqual(od))
                .and(PedidosSpecs.oeEqual(oe))
                .and(PedidosSpecs.adEqual(ad))
                .and(PedidosSpecs.dnpEqual(dnp))
                .and(PedidosSpecs.tratamentoLike(tratamento))
                .and(PedidosSpecs.tipoLenteLike(tipoLente));

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public PedidoResponseDto update(Integer id, PedidoRegisterDto dto){

        Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        Cliente cliente = pedido.getCliente();
        Laboratorio laboratorio = pedido.getLaboratorio();

        mapper.updatePedido(pedido, dto, cliente, laboratorio);

        Pedido pedidoAtualizado = repository.save(pedido);

        return mapper.toDto(pedidoAtualizado);

    }


}
