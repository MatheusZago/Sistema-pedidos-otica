package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.PedidoRegisterDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toEntity(
            PedidoRegisterDto dto,
            Cliente cliente,
            Laboratorio laboratorio
    ) {
        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setLaboratorio(laboratorio);
        pedido.setCusto(dto.custo());
        pedido.setArmacao(dto.armacao());
        pedido.setOd(dto.od());
        pedido.setOe(dto.oe());
        pedido.setAd(dto.ad());
        pedido.setDnp(dto.dnp());
        pedido.setTratamento(dto.tratamento());
        pedido.setTipoLente(dto.tipoLente());

        return pedido;
    }
}
