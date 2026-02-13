package com.matheusluizago.backend.factory;

import com.matheusluizago.backend.dto.pedidoDto.PedidoRegisterDto;
import com.matheusluizago.backend.dto.pedidoDto.PedidoResponseDto;
import com.matheusluizago.backend.model.Cliente;
import com.matheusluizago.backend.model.Laboratorio;
import com.matheusluizago.backend.model.Lente;
import com.matheusluizago.backend.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoFactory {

    private static final Integer DEFAULT_ID = 1;
    private static final String DEFAULT_ARMACAO = "Armação A";
    private static final String DEFAULT_IMG_ARMACAO = "urlarmacaoA";
    private static final BigDecimal DEFAULT_OD_PERTO = BigDecimal.valueOf(1.25);
    private static final BigDecimal DEFAULT_OD_LONGE = BigDecimal.valueOf(1.25);
    private static final BigDecimal DEFAULT_OE_PERTO = BigDecimal.valueOf(1.25);
    private static final BigDecimal DEFAULT_OE_LONGE = BigDecimal.valueOf(1.25);
    private static final BigDecimal DEFAULT_AD = BigDecimal.valueOf(5);
    private static final BigDecimal DEFAULT_DNP = BigDecimal.valueOf(2);
    private static final LocalDateTime DEFAULT_DATA_ENTREGA = LocalDateTime.now().minusDays(7);

    public static Pedido createValidPedido(){
        Pedido pedido = new Pedido();
        pedido.setId(DEFAULT_ID);
        pedido.setCliente(ClienteFactory.createValidCliente());
        pedido.setLaboratorio(LaboratorioFactory.createValidLaboratorio());
        pedido.setLente(LenteFactory.createValidLente());
        pedido.setArmacao(DEFAULT_ARMACAO);
        pedido.setImgArmacao(DEFAULT_IMG_ARMACAO);
        pedido.setOdPerto(DEFAULT_OD_PERTO);
        pedido.setOdLonge(DEFAULT_OD_LONGE);
        pedido.setOePerto(DEFAULT_OE_PERTO);
        pedido.setOeLonge(DEFAULT_OE_LONGE);
        pedido.setAd(DEFAULT_AD);
        pedido.setDnp(DEFAULT_DNP);
        pedido.setDataEntrega(DEFAULT_DATA_ENTREGA);
        return pedido;
    }

    public static Pedido createValidPedidoWithoutId(){
        Pedido pedido = createValidPedido();
        pedido.setId(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoCliente(){
        Pedido pedido = createValidPedido();
        pedido.setCliente(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoLaboratorio(){
        Pedido pedido = createValidPedido();
        pedido.setLaboratorio(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoLente(){
        Pedido pedido = createValidPedido();
        pedido.setLente(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoArmacao(){
        Pedido pedido = createValidPedido();
        pedido.setArmacao("");
        return pedido;
    }

    public static Pedido createInvalidPedidoOdPerto(){
        Pedido pedido = createValidPedido();
        pedido.setOdPerto(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoOdLonge(){
        Pedido pedido = createValidPedido();
        pedido.setOdLonge(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoOePerto(){
        Pedido pedido = createValidPedido();
        pedido.setOePerto(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoOeLonge(){
        Pedido pedido = createValidPedido();
        pedido.setOeLonge(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoAd(){
        Pedido pedido = createValidPedido();
        pedido.setAd(null);
        return pedido;
    }

    public static Pedido createInvalidPedidoDnp(){
        Pedido pedido = createValidPedido();
        pedido.setDnp(null);
        return pedido;
    }

    //img armacao, od perto, od longe, oe perto, oe longe, ad, dnp, data entrega

    //REGISTER DTO
    public static PedidoRegisterDto createValidPedidoRegisterDto(){
        return new PedidoRegisterDto(
                1,
                1,
                1,
                DEFAULT_ARMACAO,
                DEFAULT_IMG_ARMACAO,
                DEFAULT_OD_PERTO,
                DEFAULT_OD_LONGE,
                DEFAULT_OE_PERTO,
                DEFAULT_OE_LONGE,
                DEFAULT_AD,
                DEFAULT_DNP,
                DEFAULT_DATA_ENTREGA
        );

    }

    //UPDATE DTO

    //RESPONSE DTO
    public static PedidoResponseDto createValidPedidoResponseDto(){

        Cliente cliente = ClienteFactory.createValidCliente();
        Laboratorio lab = LaboratorioFactory.createValidLaboratorio();
        Lente lente = LenteFactory.createValidLente();

        return new PedidoResponseDto(
                DEFAULT_ID,
                cliente.getId(),
                cliente.getNome(),
                cliente.getFoto(),
                lab.getId(),
                lab.getNome(),
                lente.getId(),
                lente.getTipoLente(),
                lente.getCusto(),
                lente.getTratamento(),
                lente.getIndice(),
                lente.getValorVenda(),
                DEFAULT_ARMACAO,
                DEFAULT_OD_PERTO,
                DEFAULT_OD_LONGE,
                DEFAULT_OE_PERTO,
                DEFAULT_OE_LONGE,
                DEFAULT_AD,
                DEFAULT_DNP,
                DEFAULT_DATA_ENTREGA

        );
    }
}
