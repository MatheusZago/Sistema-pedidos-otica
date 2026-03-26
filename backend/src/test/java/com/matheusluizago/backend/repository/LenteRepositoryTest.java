package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.factory.LenteFactory;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.repository.specs.LenteSpecs;
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
public class LenteRepositoryTest {

    @Autowired
    private LenteRepository lenteRepository;

    private Lente validLente;

    @BeforeEach
    void setUp() {
        validLente = LenteFactory.createValidLenteWithoutId();
    }

    @Test
    void shouldFindLenteByIdUsingSpecification() {
        Lente savedLente = lenteRepository.save(validLente);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.idEqual(savedLente.getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedLente.getId(), result.get(0).getId());
    }

    @Test
    void shouldFindLenteByTipoLenteLikeIgnoringCase() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente Multifocal");
        lente1.setIndice("Indice A");
        lente1.setTratamento("Tratamento A");
        lente1.setCusto(BigDecimal.valueOf(50.00));
        lente1.setValorVenda(BigDecimal.valueOf(80.00));

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente Visão Simples");
        lente2.setIndice("Indice B");
        lente2.setTratamento("Tratamento B");
        lente2.setCusto(BigDecimal.valueOf(60.00));
        lente2.setValorVenda(BigDecimal.valueOf(90.00));

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.tipoLenteLike("multifocal")
        );

        assertEquals(1, result.size());
        assertEquals("Lente Multifocal", result.get(0).getTipoLente());
    }

    @Test
    void shouldFindLenteByCustoEqual() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente A");
        lente1.setCusto(BigDecimal.valueOf(50.00));
        lente1.setValorVenda(BigDecimal.valueOf(80.00));

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B");
        lente2.setCusto(BigDecimal.valueOf(70.00));
        lente2.setValorVenda(BigDecimal.valueOf(100.00));

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.custoEqual(BigDecimal.valueOf(50.00))
        );

        assertEquals(1, result.size());
        assertEquals("Lente A", result.get(0).getTipoLente());
        assertEquals(BigDecimal.valueOf(50.00), result.get(0).getCusto());
    }

    @Test
    void shouldFindLenteByIndiceLikeIgnoringCase() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente A");
        lente1.setIndice("Indice Premium");

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B");
        lente2.setIndice("Indice Básico");

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.indiceLike("premium")
        );

        assertEquals(1, result.size());
        assertEquals("Indice Premium", result.get(0).getIndice());
    }

    @Test
    void shouldFindLenteByTratamentoLikeIgnoringCase() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente A");
        lente1.setTratamento("Antirreflexo");

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B");
        lente2.setTratamento("Filtro Azul");

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.tratamentoLike("antirreflexo")
        );

        assertEquals(1, result.size());
        assertEquals("Antirreflexo", result.get(0).getTratamento());
    }

    @Test
    void shouldFindLenteByValorVendaEqual() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente A");
        lente1.setValorVenda(BigDecimal.valueOf(80.00));

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente B");
        lente2.setValorVenda(BigDecimal.valueOf(120.00));

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        List<Lente> result = lenteRepository.findAll(
                LenteSpecs.valorVendaEqual(BigDecimal.valueOf(80.00))
        );

        assertEquals(1, result.size());
        assertEquals("Lente A", result.get(0).getTipoLente());
        assertEquals(BigDecimal.valueOf(80.00), result.get(0).getValorVenda());
    }

    @Test
    void shouldFindLenteUsingCombinedSpecifications() {
        Lente lente1 = LenteFactory.createValidLenteWithoutId();
        lente1.setTipoLente("Lente Multifocal Premium");
        lente1.setIndice("Indice 1.67");
        lente1.setTratamento("Antirreflexo");
        lente1.setCusto(BigDecimal.valueOf(100.00));
        lente1.setValorVenda(BigDecimal.valueOf(180.00));

        Lente lente2 = LenteFactory.createValidLenteWithoutId();
        lente2.setTipoLente("Lente Multifocal Comum");
        lente2.setIndice("Indice 1.50");
        lente2.setTratamento("Filtro Azul");
        lente2.setCusto(BigDecimal.valueOf(90.00));
        lente2.setValorVenda(BigDecimal.valueOf(150.00));

        lenteRepository.save(lente1);
        lenteRepository.save(lente2);

        Specification<Lente> specs = Specification
                .where(LenteSpecs.tipoLenteLike("multifocal"))
                .and(LenteSpecs.valorVendaEqual(BigDecimal.valueOf(180.00)));

        List<Lente> result = lenteRepository.findAll(specs);

        assertEquals(1, result.size());
        assertEquals("Lente Multifocal Premium", result.get(0).getTipoLente());
        assertEquals(BigDecimal.valueOf(180.00), result.get(0).getValorVenda());
    }
}