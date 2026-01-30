package com.matheusluizago.backend.repository;

import com.matheusluizago.backend.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer>, JpaSpecificationExecutor<Laboratorio> {
}
