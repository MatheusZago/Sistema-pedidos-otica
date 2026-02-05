package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {


    public Pedido toEntity(
            PedidoRegisterDto dto,
            Cliente cliente,
            Laboratorio laboratorio,
            Lente lente
    ) {
        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setLaboratorio(laboratorio);
        pedido.setLente(lente);
        pedido.setArmacao(dto.armacao());
        pedido.setOd(dto.od());
        pedido.setOe(dto.oe());
        pedido.setAd(dto.ad());
        pedido.setDnp(dto.dnp());


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
                pedido.getLente().getId(),
                pedido.getLente().getTipoLente(),
                pedido.getLente().getCusto(),
                pedido.getLente().getTratamento(),
                pedido.getLente().getIndice(),
                pedido.getLente().getValorVenda(),
                pedido.getArmacao(),
                pedido.getOd(),
                pedido.getOe(),
                pedido.getAd(),
                pedido.getDnp(),
                pedido.getDateRegister()
        );
    }

    public void updatePedido(Pedido pedido, PedidoRegisterDto dto, Cliente cliente, Laboratorio laboratorio, Lente lente){

        if(dto.clienteId() != null){
            pedido.setCliente(cliente);
        }
        if(dto.laboratorioId() != null){
            pedido.setLaboratorio(laboratorio);
        }
        if(dto.lenteId() != null){
            pedido.setLente(lente);
        }
        if(dto.armacao() != null){
            pedido.setArmacao(dto.armacao());
        }
        if(dto.od() != null){
            pedido.setOd(dto.od());
        }
        if(dto.oe() != null){
            pedido.setOd(dto.oe());
        }
        if(dto.ad() != null){
            pedido.setAd(dto.ad());
        }
        if(dto.dnp() != null){
            pedido.setDnp(dto.dnp());
        }
    }
}
