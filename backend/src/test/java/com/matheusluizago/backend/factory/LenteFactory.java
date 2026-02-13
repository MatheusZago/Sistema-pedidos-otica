package com.matheusluizago.backend.factory;

import com.matheusluizago.backend.dto.lenteDto.LenteRegisterDto;
import com.matheusluizago.backend.dto.lenteDto.LenteUpdateDto;
import com.matheusluizago.backend.model.Lente;

import java.math.BigDecimal;

public class LenteFactory {

    private static final Integer DEFAULT_ID = 1;
    private static final String DEFAULT_TIPO_LENTE = "Lente A";
    private static final BigDecimal DEFAULT_CUSTO = BigDecimal.valueOf(50.00);
    private static final String DEFAULT_INDICE = "Indice A";
    private static final String DEFAULT_TRATAMENTO = "Tratamento A";
    private static final BigDecimal DEFAULT_VALOR_VENDA = BigDecimal.valueOf(80.00);

    public static Lente createValidLente(){
        Lente lente = new Lente();
        lente.setId(DEFAULT_ID);
        lente.setTipoLente(DEFAULT_TIPO_LENTE);
        lente.setCusto(DEFAULT_CUSTO);
        lente.setIndice(DEFAULT_INDICE);
        lente.setTratamento(DEFAULT_TRATAMENTO);
        lente.setValorVenda(DEFAULT_VALOR_VENDA);
        return lente;
    }

    public static Lente createValidLenteWithoutId(){
        Lente lente = createValidLente();
        lente.setId(null);
        return lente;
    }

    public static Lente createInvalidLenteTipoLente(){
        Lente lente = createValidLente();
        lente.setTipoLente("");
        return lente;
    }

    public static Lente createInvalidLenteCusto(){
        Lente lente = createValidLente();
        lente.setCusto(null);
        return lente;
    }

    public static Lente createInvalidLenteIndice(){
        Lente lente = createValidLente();
        lente.setIndice("");
        return lente;
    }

    public static Lente createInvalidLenteTratamento(){
        Lente lente = createValidLente();
        lente.setTratamento("");
        return lente;
    }

    public static Lente createInvalidLenteValorVenda(){
        Lente lente = createValidLente();
        lente.setValorVenda(null);
        return lente;
    }

    //REGISTER DTO
    public static LenteRegisterDto createValidLenteRegisterDto(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteRegisterDto createInvalidLenteRegisterDtoTipoLente(){
        return new LenteRegisterDto(
                "",
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteRegisterDto createInvalidLenteRegisterDtoCusto(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                null,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteRegisterDto createInvalidLenteRegisterDtoTratamento(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                "",
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteRegisterDto createInvalidLenteRegisterDtoIndice(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                "",
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteRegisterDto createInvalidLenteRegisterDtoValorVenda(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                null
        );
    }

    //UPDATE DTO
    public static LenteUpdateDto createValidLenteUpdateDto(){
        return new LenteUpdateDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }

    public static LenteUpdateDto createValidLenteUpdateDtoFewerFields(){
        return new LenteUpdateDto(
                "LENTE B",
                null,
                null,
                null,
                null
        );
    }

    //RESPONSE DTO
    public static LenteRegisterDto createValidLenteResponseDto(){
        return new LenteRegisterDto(
                DEFAULT_TIPO_LENTE,
                DEFAULT_CUSTO,
                DEFAULT_TRATAMENTO,
                DEFAULT_INDICE,
                DEFAULT_VALOR_VENDA
        );
    }
}
