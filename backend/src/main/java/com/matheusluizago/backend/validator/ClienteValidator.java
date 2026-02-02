package com.matheusluizago.backend.validator;

import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteValidator {

    private ClienteRepository repository;

    public ClienteValidator(ClienteRepository repository){
        this.repository = repository;
    }

    public void validate(Cliente cliente){
        if (existsRegisteredCliente(cliente)) {

            throw new RuntimeException("Cliente já registrado!");
        }
    }

    private boolean existsRegisteredCliente(Cliente cliente) {
        Optional<Cliente> clienteEncontrado = repository.findByEmail(cliente.getEmail());

        //Cadastro novo
        if(cliente.getId() == null){
            return clienteEncontrado.isPresent();
        }

        //Update, ignora o próprio registro
        return clienteEncontrado.isPresent() && !cliente.getId().equals(clienteEncontrado.get().getId());
    }

}
