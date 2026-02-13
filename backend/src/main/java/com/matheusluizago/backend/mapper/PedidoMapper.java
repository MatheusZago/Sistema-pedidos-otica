package com.matheusluizago.backend.mapper;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoUpdateDto;
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
        pedido.setOdPerto(dto.odPerto());
        pedido.setOdLonge(dto.odLonge());
        pedido.setOePerto(dto.oePerto());
        pedido.setOeLonge(dto.oeLonge());
        pedido.setAd(dto.ad());
        pedido.setDnp(dto.dnp());
        pedido.setDataEntrega(dto.dataEntrega());

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
                pedido.getOdPerto(),
                pedido.getOdLonge(),
                pedido.getOePerto(),
                pedido.getOeLonge(),
                pedido.getAd(),
                pedido.getDnp(),
                pedido.getDateRegister()
        );
    }

    public void updatePedido(Pedido pedido, PedidoUpdateDto dto, Cliente cliente, Laboratorio laboratorio, Lente lente){

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
        if(dto.odPerto() != null){
            pedido.setOdPerto(dto.odPerto());
        }
        if(dto.oeLonge() != null){
            pedido.setOdLonge(dto.odLonge());
        }
        if(dto.oePerto() != null){
            pedido.setOePerto(dto.oePerto());
        }
        if(dto.oeLonge() != null){
            pedido.setOeLonge(dto.oeLonge());
        }
        if(dto.ad() != null){
            pedido.setAd(dto.ad());
        }
        if(dto.dnp() != null){
            pedido.setDnp(dto.dnp());
        }
    }
}
