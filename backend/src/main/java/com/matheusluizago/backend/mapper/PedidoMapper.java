package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
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

    public void updatePedido(Pedido pedido, PedidoRegisterDto dto, Cliente cliente, Laboratorio laboratorio){

        if(dto.clienteId() != null){
            pedido.setCliente(cliente);
        }
        if(dto.laboratorioId() != null){
            pedido.setLaboratorio(laboratorio);
        }
        if(dto.custo() != null){
            pedido.setCusto(dto.custo());
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
        if(dto.tratamento() != null){
            pedido.setTratamento(dto.tratamento());
        }
        if(dto.tipoLente() != null){
            pedido.setTipoLente(dto.tipoLente());
        }

    }
}
