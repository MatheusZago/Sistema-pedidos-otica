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

    @Column(name = "cnpj", length = 150, nullable = false)
    private String cnpj;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
