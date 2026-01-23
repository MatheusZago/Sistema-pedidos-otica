package com.matheusluizago.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "laboratorios")
@EntityListeners(AuditingEntityListener.class)
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laboratorio_seq")
    @SequenceGenerator(
            name = "laboratorio_seq",
            sequenceName = "laboratorios_id_seq",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "endereco", length = 150, nullable = false)
    private String endereco;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime dateUpdate;

}
