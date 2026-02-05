package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.model.Lente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LenteRepository extends JpaRepository<Lente, Integer>, JpaSpecificationExecutor<Lente> {
}
