package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.model.Lente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.Optional;

public interface LenteRepository extends JpaRepository<Lente, Integer>, JpaSpecificationExecutor<Lente> {
    Optional<Lente> findByTipoLenteAndCustoAndTratamentoAndIndiceAndValorVenda(
            String tipoLente, BigDecimal custo, String tratamento, String indice, BigDecimal valorVenda);
}
