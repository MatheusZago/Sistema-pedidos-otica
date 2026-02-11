package com.matheusluizago.backend.repository.specs;

import com.matheusluizago.backend.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PedidosSpecs {

    public static Specification<Pedido> idEqual(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? null :
                  criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Pedido> idClienteEqual(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? null :
                        criteriaBuilder.equal(root.join("cliente").get("id"), id);
    }

    public static Specification<Pedido> nomeClienteLike(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;

            return cb.like(
                    cb.lower(root.join("cliente").get("nome")),
                    "%" + nome.toLowerCase() + "%");
        };
    }


    public static Specification<Pedido> emailClienteLike(String email){
        return (root, query, criteriaBuilder) ->
                email == null ? null :
                        criteriaBuilder.like(root.join("cliente").get("email"),
                                "%" + email.toLowerCase() + "%");
    }

    public static Specification<Pedido> telefoneClienteLike(String telefone){
        return (root, query, criteriaBuilder) ->
                telefone == null ? null :
                        criteriaBuilder.like(root.join("cliente").get("telefone"),
                                "%" + telefone.toLowerCase() + "%");
    }

    public static Specification<Pedido> idLaboratorioEqual(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? null :
                        criteriaBuilder.equal(root.join("laboratorio").get("id"), id);
    }

    public static Specification<Pedido> nomeLaboratorioLike(String nomeLaboratorio){
        return (root, query, criteriaBuilder) ->
                nomeLaboratorio == null ? null :
                        criteriaBuilder.like(root.join("laboratorio").get("nome"),
                                "%" + nomeLaboratorio.toLowerCase() + "%");
    }

    public static Specification<Pedido> cnpjLaboratorioLike(String cnpj){
        return (root, query, criteriaBuilder) ->
                cnpj == null ? null :
                        criteriaBuilder.like(root.join("laboratorio").get("cnpj"),
                                "%" + cnpj.toLowerCase() + "%");
    }

    public static Specification<Pedido> enderecoLaboratorioLike(String enderecoLaboratorio){
        return (root, query, criteriaBuilder) ->
                enderecoLaboratorio == null ? null :
                        criteriaBuilder.like(root.join("laboratorio").get("endereco"),
                                "%" + enderecoLaboratorio.toLowerCase() + "%");
    }

    public static Specification<Pedido> idLenteEqual(Integer id){
        return (root, query, criteriaBuilder) ->
                id == null ? null :
                        criteriaBuilder.equal(root.join("lente").get("id"), id);
    }

    public static Specification<Pedido> custoLenteEqual(BigDecimal custo){
        return (root, query, criteriaBuilder) ->
                custo == null ? null :
                    criteriaBuilder.equal(root.join("lente").get("custo"), custo);
    }

    public static Specification<Pedido> tratamentoLenteLike(String tratamento){
        return (root, query, criteriaBuilder) ->
                tratamento == null ? null :
                        criteriaBuilder.like(root.join("lente").get("tratamento"),
                                "%" + tratamento.toLowerCase() + "%");
    }

    public static Specification<Pedido> indiceLenteLike(String indice){
        return (root, query, criteriaBuilder) ->
                indice == null ? null :
                        criteriaBuilder.like(root.join("lente").get("indice"),
                                "%" + indice.toLowerCase() + "%");
    }

    public static Specification<Pedido> valorVendaLenteEqual(BigDecimal valorVenda){
        return (root, query, criteriaBuilder) ->
                valorVenda == null ? null :
                        criteriaBuilder.equal(root.join("lente").get("valorVenda"), valorVenda);
    }

    public static Specification<Pedido> armacaoLike(String armacao){
        return (root, query, criteriaBuilder) ->
                armacao == null ? null :
                        criteriaBuilder.like(root.get("armacao"),
                                "%" + armacao.toLowerCase() + "%");
    }

    public static Specification<Pedido> odEqual(BigDecimal od){
        return (root, query, criteriaBuilder) ->
                od == null ? null :
                        criteriaBuilder.equal(root.get("od"), od);
    }

    public static Specification<Pedido> oeEqual(BigDecimal oe){
        return (root, query, criteriaBuilder) ->
                oe == null ? null :
                        criteriaBuilder.equal(root.get("oe"), oe);
    }

    public static Specification<Pedido> adEqual(BigDecimal ad){
        return (root, query, criteriaBuilder) ->
                ad == null ? null :
                        criteriaBuilder.equal(root.get("ad"), ad);
    }

    public static Specification<Pedido> dnpEqual(BigDecimal dnp){
        return (root, query, criteriaBuilder) ->
                dnp == null ? null :
                        criteriaBuilder.equal(root.get("dnp"), dnp);
    }


    public static Specification<Pedido> tipoLenteLike(String tipoLente){
        return (root, query, criteriaBuilder) ->
                tipoLente == null ? null :
                        criteriaBuilder.like(root.get("tipoLente"),
                                "%" + tipoLente.toLowerCase() + "%");
    }

}
