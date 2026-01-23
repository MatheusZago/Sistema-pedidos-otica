package com.matheusluizago.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientes_seq")
    @SequenceGenerator(
            name = "clientes_seq",
            sequenceName = "clientes_id_seq",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "telefone", length = 11, nullable = false)
    private String telefone;

    @Column(name = "email", length = 80)
    private String email;

    @Column(name = "foto")
    private String foto;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime dateUpdate;

}
