package com.alessandromelo.dto.agenteoperacoes.enviarmetricas;

import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class EnviarMetricasResponseDTO {

    private Long id;
    private MetricasTipo tipo;
    private String valor;
    private String unidade;
    private LocalDateTime dataColeta;


    public EnviarMetricasResponseDTO() {
    }

    public EnviarMetricasResponseDTO(Long id, MetricasTipo tipo, String valor, String unidade, LocalDateTime dataColeta) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataColeta = dataColeta;
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
}
