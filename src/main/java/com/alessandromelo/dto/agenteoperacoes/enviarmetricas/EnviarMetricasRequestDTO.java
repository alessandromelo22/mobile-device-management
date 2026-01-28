package com.alessandromelo.dto.agenteoperacoes.enviarmetricas;

import com.alessandromelo.enums.MetricasTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EnviarMetricasRequestDTO {

    @NotNull(message = "O tipo da Métrica deve ser informado!")
    private MetricasTipo tipo;
    @NotBlank(message = "O valor da Métrica deve ser informado!")
    private String valor;
    @NotBlank(message = "A unidade da Métrica deve ser informada!")
    private String unidade;
    @NotNull(message = "A data de coleta da Métrica deve ser informada!")
    private LocalDateTime dataColeta;


    public EnviarMetricasRequestDTO() {
    }

    public EnviarMetricasRequestDTO(MetricasTipo tipo, String valor, String unidade, LocalDateTime dataColeta) {
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataColeta = dataColeta;
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
