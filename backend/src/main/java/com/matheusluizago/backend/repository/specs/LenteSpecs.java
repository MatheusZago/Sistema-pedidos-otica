package com.matheusluizago.backend.repository.specs;

import com.matheusluizago.backend.model.Lente;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class LenteSpecs {

    public static Specification<Lente> idEqual(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? null:
                        criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Lente> tipoLenteLike(String tipoLente){
        return (root, query, criteriaBuilder) ->
                tipoLente == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("tipoLente")),
                                "%" + tipoLente.toLowerCase() + "%"
                        );
    }

    public static Specification<Lente> custoEqual(BigDecimal custo){
        return (root, query, criteriaBuilder) ->
                custo == null ? null:
                        criteriaBuilder.equal(root.get("custo"), custo);
    }

    public static Specification<Lente> indiceLike(String indice){
        return (root, query, criteriaBuilder) ->
                indice == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("indice")),
                                "%" + indice.toLowerCase() + "%"
                        );
    }

    public static Specification<Lente> valorVendaEqual(BigDecimal valorVenda){
        return (root, query, criteriaBuilder) ->
                valorVenda == null ? null:
                        criteriaBuilder.equal(root.get("valorVenda"), valorVenda);
    }

    public static Specification<Lente> tratamentoLike(String tratamento){
        return (root, query, criteriaBuilder) ->
                tratamento == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("tratamento")),
                                "%" + tratamento.toLowerCase() + "%"
                        );
    }

}
