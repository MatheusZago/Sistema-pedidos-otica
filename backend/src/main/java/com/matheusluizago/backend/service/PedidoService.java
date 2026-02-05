package com.matheusluizago.backend.service;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
import com.matheusluizago.backend.exceptions.ResourceNotFoundException;
import com.matheusluizago.backend.mapper.PedidoMapper;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.ClienteRepository;
import com.matheusluizago.backend.repository.LaboratorioRepository;
import com.matheusluizago.backend.repository.LenteRepository;
import com.matheusluizago.backend.repository.PedidoRepository;
import com.matheusluizago.backend.repository.specs.PedidosSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final LaboratorioRepository labRepository;
    private final LenteRepository lenteRepository;
    private final PedidoMapper mapper;

    public PedidoService(PedidoRepository repository,
                         ClienteRepository clienteRepository,
                         LaboratorioRepository labRepository,
                         LenteRepository lenteRepository,
                         PedidoMapper mapper){
        this.clienteRepository = clienteRepository;
        this.labRepository = labRepository;
        this.repository = repository;
        this.lenteRepository = lenteRepository;
        this.mapper = mapper;
    }

    public Pedido save(PedidoRegisterDto dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        Laboratorio lab = labRepository.findById(dto.laboratorioId())
                .orElseThrow(() -> new ResourceNotFoundException("Laboratório não encontrado."));

        Lente lente = lenteRepository.findById(dto.lenteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lente não encontrada."));

        Pedido pedidoSalvo = mapper.toEntity(dto, cliente, lab, lente);

        return repository.save(pedidoSalvo);
    }

    public List<PedidoResponseDto> search(Integer id, Integer clienteId, String clienteNome,
                                          String clienteEmail, String clienteTelefone,
                                          Integer labId, String labNome, String labEndereco, String labCnpj,
                                          Integer lenteId, BigDecimal lenteCusto, String lenteTratamento,
                                          String lenteIndice, String tipoLente, BigDecimal valorVenda,
                                          String armacao, BigDecimal od, BigDecimal oe, BigDecimal ad, BigDecimal dnp
                                            ){

        Specification<Pedido> spec = Specification
                .where(PedidosSpecs.idEqual(id))
                .and(PedidosSpecs.idClienteEqual(clienteId))
                .and(PedidosSpecs.nomeClienteLike(clienteNome))
                .and(PedidosSpecs.emailClienteLike(clienteEmail))
                .and(PedidosSpecs.telefoneClienteLike(clienteTelefone))
                .and(PedidosSpecs.idLaboratorioEqual(labId))
                .and(PedidosSpecs.nomeLaboratorioLike(labNome))
                .and(PedidosSpecs.enderecoLaboratorioLike(labEndereco))
                .and(PedidosSpecs.cnpjLaboratorioLike(labCnpj))
                .and(PedidosSpecs.idLenteEqual(lenteId))
                .and(PedidosSpecs.custoLenteEqual(lenteCusto))
                .and(PedidosSpecs.tratamentoLenteLike(lenteTratamento))
                .and(PedidosSpecs.indiceLenteLike(lenteIndice))
                .and(PedidosSpecs.tipoLenteLike(tipoLente))
                .and(PedidosSpecs.valorVendaLenteEqual(valorVenda))
                .and(PedidosSpecs.armacaoLike(armacao))
                .and(PedidosSpecs.odEqual(od))
                .and(PedidosSpecs.oeEqual(oe))
                .and(PedidosSpecs.adEqual(ad))
                .and(PedidosSpecs.dnpEqual(dnp));


        return repository.findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public PedidoResponseDto update(Integer id, PedidoUpdateDto dto){

        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        Cliente cliente = pedido.getCliente();
        Laboratorio laboratorio = pedido.getLaboratorio();
        Lente lente = pedido.getLente();

        mapper.updatePedido(pedido, dto, cliente, laboratorio, lente);

        Pedido pedidoAtualizado = repository.save(pedido);

        return mapper.toDto(pedidoAtualizado);

    }


}
