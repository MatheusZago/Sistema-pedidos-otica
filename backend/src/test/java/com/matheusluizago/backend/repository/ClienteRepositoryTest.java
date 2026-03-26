package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.dto.clienteDto.ClienteRegisterDto;
import com.matheusluizago.backend.factory.ClienteFactory;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.specs.ClienteSpecs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente validCliente;
    private Cliente invalidNameClienteRegister;
    private Cliente invalidEmailClienteRegister;
    private Cliente invalidTelefoneClienteRegister;

    @BeforeEach
    void setUp(){
        validCliente = ClienteFactory.createValidCliente();
        invalidNameClienteRegister = ClienteFactory.createInvalidNomeCliente();
        invalidEmailClienteRegister = ClienteFactory.createInvalidEmailCliente();
        invalidTelefoneClienteRegister = ClienteFactory.createInvalidTelefoneCliente();

    }


    @Test
    void shouldFindClienteByEmail(){
        clienteRepository.save(validCliente);

        Optional<Cliente> result = clienteRepository.findByEmail("matheus@email.com");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldNotFindClienteByEmailWhenEmailDoesNotExist(){
        clienteRepository.save(validCliente);

        Optional<Cliente> result = clienteRepository.findByEmail("naoexiste@email.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindClienteByIdUsingSpecification(){
        Cliente savedCliente = clienteRepository.save(validCliente);

        List<Cliente> result = clienteRepository.findAll(ClienteSpecs.idEqual(savedCliente.getId()));

        assertEquals(1, result.size());
        assertEquals(savedCliente.getId(), result.get(0).getId());
    }

    @Test
    void shouldFindClienteByNomeLikeIgnoringCase(){
        Cliente cliente1 = ClienteFactory.createValidCliente();
        cliente1.setId(null);
        cliente1.setNome("Matheus Luiz");
        cliente1.setEmail("matheusluiz@email.com");

        Cliente cliente2 = ClienteFactory.createValidCliente();
        cliente2.setId(null);
        cliente2.setNome("Maria Silva");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("11987654321");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        List<Cliente> result = clienteRepository.findAll(ClienteSpecs.nomeLike("matheus"));

        assertEquals(1, result.size());
        assertEquals("Matheus Luiz", result.get(0).getNome());
    }

    @Test
    void shouldFindClienteByTelefoneLike(){
        Cliente cliente1 = ClienteFactory.createValidCliente();
        cliente1.setId(null);
        cliente1.setNome("Carlos");
        cliente1.setTelefone("11912345678");
        cliente1.setEmail("carlos@email.com");

        Cliente cliente2 = ClienteFactory.createValidCliente();
        cliente2.setId(null);
        cliente2.setNome("Ana");
        cliente2.setTelefone("11888888888");
        cliente2.setEmail("ana@email.com");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        List<Cliente> result = clienteRepository.findAll(ClienteSpecs.telefoneLike("9123"));

        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNome());
    }

    @Test
    void shouldFindClienteByEmailLike(){
        Cliente cliente1 = ClienteFactory.createValidCliente();
        cliente1.setId(null);
        cliente1.setNome("Bruno");
        cliente1.setEmail("bruno@email.com");

        Cliente cliente2 = ClienteFactory.createValidCliente();
        cliente2.setId(null);
        cliente2.setNome("Fernanda");
        cliente2.setEmail("fernanda@email.com");
        cliente2.setTelefone("11888887777");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        List<Cliente> result = clienteRepository.findAll(ClienteSpecs.emailLike("bruno"));

        assertEquals(1, result.size());
        assertEquals("Bruno", result.get(0).getNome());
        assertEquals("bruno@email.com", result.get(0).getEmail());
    }

    @Test
    void shouldFindClienteUsingCombinedSpecifications(){
        Cliente cliente1 = ClienteFactory.createValidCliente();
        cliente1.setId(null);
        cliente1.setNome("Matheus Luiz");
        cliente1.setEmail("matheus@email.com");

        Cliente cliente2 = ClienteFactory.createValidCliente();
        cliente2.setId(null);
        cliente2.setNome("Matheus Souza");
        cliente2.setEmail("souza@email.com");
        cliente2.setTelefone("11888888888");

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        Specification<Cliente> specs = Specification
                .where(ClienteSpecs.nomeLike("matheus"))
                .and(ClienteSpecs.emailLike("matheus@email.com"));

        List<Cliente> result = clienteRepository.findAll(specs);

        assertEquals(1, result.size());
        assertEquals("Matheus Luiz", result.get(0).getNome());
        assertEquals("matheus@email.com", result.get(0).getEmail());
    }


}
