package com.alessandromelo.entity;

import com.alessandromelo.enums.DispositivoStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private String marca;
    @Column(unique = true)
    private String numeroSerie;
    private String sistemaOperacional;
    private String versaoSO;
    @Enumerated(EnumType.STRING)
    private DispositivoStatus status; //enum (ATIVO, INATIVO, EM_MANUTENCAO, DESCARTADO)
    private LocalDate dataAquisicao;
    private LocalDateTime dataUltimaAtualizacao; //atualização do SO
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario; // FK

    @OneToOne(mappedBy = "dispositivo")
    private Agente agente;



    public Dispositivo() {
    }

    public Dispositivo(String modelo, String marca, String numeroSerie, String sistemaOperacional, String versaoSO, DispositivoStatus status, LocalDate dataAquisicao, LocalDateTime dataUltimaAtualizacao, String observacoes, Usuario usuario, Agente agente) {
        this.modelo = modelo;
        this.marca = marca;
        this.numeroSerie = numeroSerie;
        this.sistemaOperacional = sistemaOperacional;
        this.versaoSO = versaoSO;
        this.status = status;
        this.dataAquisicao = dataAquisicao;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.observacoes = observacoes;
        this.usuario = usuario;
        this.agente = agente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getVersaoSO() {
        return versaoSO;
    }

    public void setVersaoSO(String versaoSO) {
        this.versaoSO = versaoSO;
    }

    public DispositivoStatus getStatus() {
        return status;
    }

    public void setStatus(DispositivoStatus status) {
        this.status = status;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }
}
