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
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorio_id", nullable = false)
    private Laboratorio laboratorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lente_id", nullable = false)
    private Lente lente;

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

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dateRegister;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime dateUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getArmacao() {
        return armacao;
    }

    public void setArmacao(String armacao) {
        this.armacao = armacao;
    }

    public String getImgArmacao() {
        return imgArmacao;
    }

    public void setImgArmacao(String imgArmacao) {
        this.imgArmacao = imgArmacao;
    }

    public BigDecimal getOd() {
        return od;
    }

    public void setOd(BigDecimal od) {
        this.od = od;
    }

    public BigDecimal getOe() {
        return oe;
    }

    public void setOe(BigDecimal oe) {
        this.oe = oe;
    }

    public BigDecimal getAd() {
        return ad;
    }

    public void setAd(BigDecimal ad) {
        this.ad = ad;
    }

    public BigDecimal getDnp() {
        return dnp;
    }

    public void setDnp(BigDecimal dnp) {
        this.dnp = dnp;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Lente getLente() {
        return lente;
    }

    public void setLente(Lente lente) {
        this.lente = lente;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
