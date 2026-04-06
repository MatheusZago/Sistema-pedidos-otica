package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.factory.LaboratorioFactory;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.repository.specs.LaboratorioSpecs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class LaboratorioRepositoryTest {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    private Laboratorio validLaboratorio;

    @BeforeEach
    void setUp() {
        validLaboratorio = LaboratorioFactory.createValidLaboratorioWithoutId();
        validLaboratorio.setEmail("laboratorioa@email.com");
        validLaboratorio.setTelefone("11999999999");
        validLaboratorio.setCnpj("12345678000195"); // CNPJ válido
    }

    @Test
    void shouldFindLaboratorioByCnpj() {
        laboratorioRepository.save(validLaboratorio);

        Optional<Laboratorio> result = laboratorioRepository.findByCnpj("12345678000195");

        assertTrue(result.isPresent());
        assertEquals("Laboratório A", result.get().getNome());
    }

    @Test
    void shouldNotFindLaboratorioByCnpjWhenCnpjDoesNotExist() {
        laboratorioRepository.save(validLaboratorio);

        Optional<Laboratorio> result = laboratorioRepository.findByCnpj("19131243000197"); // válido, mas não salvo

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindLaboratorioByIdUsingSpecification() {
        Laboratorio savedLaboratorio = laboratorioRepository.save(validLaboratorio);

        List<Laboratorio> result = laboratorioRepository.findAll(
                LaboratorioSpecs.idEqual(savedLaboratorio.getId())
        );

        assertEquals(1, result.size());
        assertEquals(savedLaboratorio.getId(), result.get(0).getId());
    }

    @Test
    void shouldFindLaboratorioByNomeLikeIgnoringCase() {
        Laboratorio laboratorio1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio1.setNome("Laboratório Central");
        laboratorio1.setCnpj("12345678000195");
        laboratorio1.setEmail("central@email.com");
        laboratorio1.setTelefone("11911111111");

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Ótica Lab");
        laboratorio2.setCnpj("27865757000102");
        laboratorio2.setEndereco("Endereço B");
        laboratorio2.setEmail("oticalab@email.com");
        laboratorio2.setTelefone("11922222222");

        laboratorioRepository.save(laboratorio1);
        laboratorioRepository.save(laboratorio2);

        List<Laboratorio> result = laboratorioRepository.findAll(
                LaboratorioSpecs.nomeLike("central")
        );

        assertEquals(1, result.size());
        assertEquals("Laboratório Central", result.get(0).getNome());
    }

    @Test
    void shouldFindLaboratorioByEnderecoLike() {
        Laboratorio laboratorio1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio1.setNome("Laboratório A");
        laboratorio1.setEndereco("Rua das Flores");
        laboratorio1.setCnpj("12345678000195");
        laboratorio1.setEmail("labA@email.com");
        laboratorio1.setTelefone("11933333333");

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório B");
        laboratorio2.setEndereco("Avenida Paulista");
        laboratorio2.setCnpj("27865757000102");
        laboratorio2.setEmail("labB@email.com");
        laboratorio2.setTelefone("11944444444");

        laboratorioRepository.save(laboratorio1);
        laboratorioRepository.save(laboratorio2);

        List<Laboratorio> result = laboratorioRepository.findAll(
                LaboratorioSpecs.enderecoLike("flores")
        );

        assertEquals(1, result.size());
        assertEquals("Laboratório A", result.get(0).getNome());
        assertEquals("Rua das Flores", result.get(0).getEndereco());
    }

    @Test
    void shouldFindLaboratorioByCnpjLike() {
        Laboratorio laboratorio1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio1.setNome("Laboratório A");
        laboratorio1.setCnpj("12345678000195");
        laboratorio1.setEmail("emailteste@email.com");
        laboratorio1.setTelefone("11955555555");

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório B");
        laboratorio2.setCnpj("19131243000197");
        laboratorio2.setEmail("laboratoriob@email.com");
        laboratorio2.setTelefone("11966666666");

        laboratorioRepository.save(laboratorio1);
        laboratorioRepository.save(laboratorio2);

        List<Laboratorio> result = laboratorioRepository.findAll(
                LaboratorioSpecs.cnpjLike("12345678")
        );

        assertEquals(1, result.size());
        assertEquals("Laboratório A", result.get(0).getNome());
        assertEquals("12345678000195", result.get(0).getCnpj());
    }

    @Test
    void shouldFindLaboratorioUsingCombinedSpecifications() {
        Laboratorio laboratorio1 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio1.setNome("Laboratório Central");
        laboratorio1.setEndereco("Rua A");
        laboratorio1.setCnpj("12345678000195");
        laboratorio1.setEmail("centralA@email.com");
        laboratorio1.setTelefone("11977777777");

        Laboratorio laboratorio2 = LaboratorioFactory.createValidLaboratorioWithoutId();
        laboratorio2.setNome("Laboratório Central");
        laboratorio2.setEndereco("Rua B");
        laboratorio2.setCnpj("27865757000102");
        laboratorio2.setEmail("centralB@email.com");
        laboratorio2.setTelefone("11988888888");

        laboratorioRepository.save(laboratorio1);
        laboratorioRepository.save(laboratorio2);

        Specification<Laboratorio> specs = Specification
                .where(LaboratorioSpecs.nomeLike("central"))
                .and(LaboratorioSpecs.cnpjLike("12345678"));

        List<Laboratorio> result = laboratorioRepository.findAll(specs);

        assertEquals(1, result.size());
        assertEquals("Laboratório Central", result.get(0).getNome());
        assertEquals("12345678000195", result.get(0).getCnpj());
    }
}