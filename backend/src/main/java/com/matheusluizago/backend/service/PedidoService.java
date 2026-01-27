package com.matheusluizago.backend.service;

import com.matheusluizago.backend.model.Pedido;
import com.matheusluizago.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository){
        this.repository = repository;
    }

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    public Optional<Pedido> getById(int id) {
        return repository.findById(id);
    }
}
