package com.matheusluizago.backend.repository.specs;

import com.matheusluizago.backend.model.Laboratorio;
import org.springframework.data.jpa.domain.Specification;

public class LaboratorioSpecs {

    public static Specification<Laboratorio> idEqual(Integer id){
        return ((root, query, criteriaBuilder) ->
                id == null ? null:
                        criteriaBuilder.equal(root.get("id"), id)
        );
    }

    public static Specification<Laboratorio> nomeLike(String nome){
        return ((root, query, criteriaBuilder) ->
                nome == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%"
                        ));
    }

    public static Specification<Laboratorio> enderecoLike(String endereco){
        return ((root, query, criteriaBuilder) ->
                endereco == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("endereco")),
                                "%" + endereco.toLowerCase() + "%"
                        ));
    }
}
