package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.PedidoResponseDto;
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

    public PedidoResponseDto toDto(Pedido pedido){

        return new PedidoResponseDto(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getFoto(),
                pedido.getLaboratorio().getId(),
                pedido.getLaboratorio().getNome(),
                pedido.getCusto(),
                pedido.getArmacao(),
                pedido.getOd(),
                pedido.getOe(),
                pedido.getAd(),
                pedido.getDnp(),
                pedido.getTratamento(),
                pedido.getTipoLente(),
                pedido.getDateRegister()
        );

    }
}
