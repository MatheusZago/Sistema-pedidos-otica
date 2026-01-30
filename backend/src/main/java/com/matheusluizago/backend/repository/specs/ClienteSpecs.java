package com.matheusluizago.backend.repository.specs;

import com.matheusluizago.backend.model.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecs {

    public static Specification<Cliente> idEqual(Integer id){
        return ((root, query, criteriaBuilder) ->
                id == null ? null:
                        criteriaBuilder.equal(root.get("id"), id)
        );
    }

    public static Specification<Cliente> nomeLike(String nome){
        return ((root, query, criteriaBuilder) ->
                nome == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nome")),
                                "%" + nome.toLowerCase() + "%"
                ));
    }

    public static Specification<Cliente> telefoneLike(String telefone){
        return ((root, query, criteriaBuilder) ->
                telefone == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("telefone")),
                                "%" + telefone.toLowerCase() + "%"
                        ));
    }

    public static Specification<Cliente> emailLike(String email){
        return ((root, query, criteriaBuilder) ->
                email == null ? null:
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        ));
    }


}
