package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.factory.PedidoFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.specs.PedidosSpecs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class PedidoRepositoryTest {

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
    private Pedido validPedido;

    @BeforeEach
    void setUp() {
        validCliente = ClienteFactory.createValidClienteWithoutId();
        validLaboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        validLente = LenteFactory.createValidLenteWithoutId();

        validCliente = clienteRepository.save(validCliente);
        validLaboratorio = laboratorioRepository.save(validLaboratorio);
        validLente = lenteRepository.save(validLente);

        validPedido = PedidoFactory.createValidPedidoWithoutId();
        validPedido.setCliente(validCliente);
        validPedido.setLaboratorio(validLaboratorio);
        validPedido.setLente(validLente);
    }

    @Test
    void shouldFindPedidoByIdUsingSpecification() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.idEqual(savedPedido.getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getId(), result.get(0).getId());
    }

    @Test
    void shouldFindPedidoByClienteIdUsingSpecification() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.idClienteEqual(savedPedido.getCliente().getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getCliente().getId(), result.get(0).getCliente().getId());
    }

    @Test
    void shouldFindPedidoByNomeClienteLike() {
        Cliente cliente1 = ClienteFactory.createValidClienteWithoutId();
        cliente1.setNome("Matheus Luiz");
        cliente1.setEmail("matheusluiz@email.com");
        cliente1 = clienteRepository.save(cliente1);

        Cliente cliente2 = ClienteFactory.createValidClienteWithoutId();
        cliente2.setNome("Maria Silva");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("11987654321");
        cliente2 = clienteRepository.save(cliente2);

        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(cliente1);
        pedido1.setLaboratorio(validLaboratorio);
        pedido1.setLente(validLente);

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(cliente2);
        pedido2.setLaboratorio(validLaboratorio);
        pedido2.setLente(validLente);

        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.nomeClienteLike("matheus")
        );

        assertEquals(1, result.size());
        assertEquals("Matheus Luiz", result.get(0).getCliente().getNome());
    }

    @Test
    void shouldFindPedidoByEmailClienteLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.emailClienteLike(savedPedido.getCliente().getEmail())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getCliente().getEmail(), result.get(0).getCliente().getEmail());
    }

    @Test
    void shouldFindPedidoByTelefoneClienteLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.telefoneClienteLike(savedPedido.getCliente().getTelefone())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getCliente().getTelefone(), result.get(0).getCliente().getTelefone());
    }

    @Test
    void shouldFindPedidoByLaboratorioIdUsingSpecification() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.idLaboratorioEqual(savedPedido.getLaboratorio().getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLaboratorio().getId(), result.get(0).getLaboratorio().getId());
    }

    @Test
    void shouldFindPedidoByNomeLaboratorioLike() {
        Laboratorio laboratorio1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio1.setNome("Laboratório Central");
        laboratorio1.setCnpj("12345678000195");
        laboratorio1.setEmail("laboratoriocentral@email.com");
        laboratorio1.setTelefone("11999999991");
        laboratorio1 = laboratorioRepository.save(laboratorio1);

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório B");
        laboratorio2.setCnpj("11222333000181");
        laboratorio2.setEndereco("Endereço B");
        laboratorio2.setEmail("laboratoriob@email.com");
        laboratorio2.setTelefone("11999999992");
        laboratorio2 = laboratorioRepository.save(laboratorio2);

        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(validCliente);
        pedido1.setLaboratorio(laboratorio1);
        pedido1.setLente(validLente);

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(validCliente);
        pedido2.setLaboratorio(laboratorio2);
        pedido2.setLente(validLente);

        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.nomeLaboratorioLike("central")
        );

        assertEquals(1, result.size());
        assertEquals("Laboratório Central", result.get(0).getLaboratorio().getNome());
    }

    @Test
    void shouldFindPedidoByCnpjLaboratorioLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.cnpjLaboratorioLike(savedPedido.getLaboratorio().getCnpj())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLaboratorio().getCnpj(), result.get(0).getLaboratorio().getCnpj());
    }

    @Test
    void shouldFindPedidoByEnderecoLaboratorioLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.enderecoLaboratorioLike(savedPedido.getLaboratorio().getEndereco())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLaboratorio().getEndereco(), result.get(0).getLaboratorio().getEndereco());
    }

    @Test
    void shouldFindPedidoByLenteIdUsingSpecification() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.idLenteEqual(savedPedido.getLente().getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLente().getId(), result.get(0).getLente().getId());
    }

    @Test
    void shouldFindPedidoByCustoLenteEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.custoLenteEqual(savedPedido.getLente().getCusto())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLente().getCusto(), result.get(0).getLente().getCusto());
    }

    @Test
    void shouldFindPedidoByTratamentoLenteLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.tratamentoLenteLike(savedPedido.getLente().getTratamento())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLente().getTratamento(), result.get(0).getLente().getTratamento());
    }

    @Test
    void shouldFindPedidoByIndiceLenteLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.indiceLenteLike(savedPedido.getLente().getIndice())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLente().getIndice(), result.get(0).getLente().getIndice());
    }

    @Test
    void shouldFindPedidoByValorVendaLenteEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.valorVendaLenteEqual(savedPedido.getLente().getValorVenda())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getLente().getValorVenda(), result.get(0).getLente().getValorVenda());
    }

    @Test
    void shouldFindPedidoByArmacaoLike() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.armacaoLike(savedPedido.getArmacao())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getArmacao(), result.get(0).getArmacao());
    }

    @Test
    void shouldFindPedidoByOdPertoEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.odPertoEqual(savedPedido.getOdPerto())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getOdPerto(), result.get(0).getOdPerto());
    }

    @Test
    void shouldFindPedidoByOdLongeEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.odLongeEqual(savedPedido.getOdLonge())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getOdLonge(), result.get(0).getOdLonge());
    }

    @Test
    void shouldFindPedidoByOePertoEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.oePertoEqual(savedPedido.getOePerto())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getOePerto(), result.get(0).getOePerto());
    }

    @Test
    void shouldFindPedidoByOeLongeEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.oeLongeEqual(savedPedido.getOeLonge())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getOeLonge(), result.get(0).getOeLonge());
    }

    @Test
    void shouldFindPedidoByAdEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.adEqual(savedPedido.getAd())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getAd(), result.get(0).getAd());
    }

    @Test
    void shouldFindPedidoByDnpEqual() {
        Pedido savedPedido = pedidoRepository.save(validPedido);

        List<Pedido> result = pedidoRepository.findAll(
                PedidosSpecs.dnpEqual(savedPedido.getDnp())
        );

        assertEquals(1, result.size());
        assertEquals(savedPedido.getDnp(), result.get(0).getDnp());
    }

    @Test
    void shouldFindPedidoUsingCombinedSpecifications() {
        Pedido pedido1 = PedidoFactory.createValidPedidoWithoutId();
        pedido1.setCliente(validCliente);
        pedido1.setLaboratorio(validLaboratorio);
        pedido1.setLente(validLente);
        pedido1.setArmacao("Armação Premium");
        pedido1.setAd(BigDecimal.valueOf(5));

        Pedido pedido2 = PedidoFactory.createValidPedidoWithoutId();
        pedido2.setCliente(validCliente);
        pedido2.setLaboratorio(validLaboratorio);
        pedido2.setLente(validLente);
        pedido2.setArmacao("Armação Comum");
        pedido2.setAd(BigDecimal.valueOf(3));

        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);

        Specification<Pedido> specs = Specification
                .where(PedidosSpecs.armacaoLike("premium"))
                .and(PedidosSpecs.adEqual(BigDecimal.valueOf(5)));

        List<Pedido> result = pedidoRepository.findAll(specs);

        assertEquals(1, result.size());
        assertEquals("Armação Premium", result.get(0).getArmacao());
        assertEquals(BigDecimal.valueOf(5), result.get(0).getAd());
    }
}