package com.alessandromelo.entity;

import com.alessandromelo.enums.MetricasTipo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MetricasDispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MetricasTipo tipo; // tipo da métrica (RAM, Bateria, CPU, etc.)
    private String valor;
    private String unidade; // unidade de medida (%, ºC, MB, GB)
    private LocalDateTime dataColeta;

    @ManyToOne
    @JoinColumn(name = "id_agente")
    private Agente agente; //FK


    public MetricasDispositivo() {
    }

    public MetricasDispositivo(Long id, MetricasTipo tipo, String valor, String unidade, LocalDateTime dataColeta, Agente agente) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataColeta = dataColeta;
        this.agente = agente;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetricasTipo getTipo() {
        return tipo;
    }

    public void setTipo(MetricasTipo tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public LocalDateTime getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDateTime dataColeta) {
        this.dataColeta = dataColeta;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }
}
