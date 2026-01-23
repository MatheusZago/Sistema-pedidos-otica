package com.matheusluizago.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedidos_seq")
    @SequenceGenerator(
            name = "pedidos_seq",
            sequenceName = "pedidos_id_seq",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente clienteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id", nullable = false)
    private Laboratorio laboratorioId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal custo;

    @Column(name = "armacao")
    private String armacao;

    @Column(name = "img_armacao")
    private String imgArmacao;

    //Olho direito
    @Column(precision = 5, scale = 2)
    private BigDecimal od;

    //Olho esquerdo
    @Column(precision = 5, scale = 2)
    private BigDecimal oe;

    //Olho adição
    @Column(precision = 5, scale = 2)
    private BigDecimal ad;

    //Distãncia Naso Pupilar

    @Column(precision = 5, scale = 2)
    private BigDecimal dnp;

    @Column(length = 120)
    private String tratamento;

    @Column(name = "tipo_lente", length = 50)
    private String tipoLente;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime dateUpdate;
}
