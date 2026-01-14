package com.alessandromelo.dto.metricasdispositivo;

import com.alessandromelo.enums.MetricasTipo;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class MetricasDispositivoRequestDTO {

    @NotBlank(message = "O tipo da Metrica deve ser informado!")
    private MetricasTipo tipo;
    @NotBlank(message = "O valor da Metrica deve ser informado!")
    private String valor;
    @NotBlank(message = "A unidade de medida da Metrica deve ser informada!")
    private String unidade;
    @NotBlank(message = "A data da coleta da Metrica deve ser informada!")
    private LocalDateTime dataColeta;

    private Long agenteId; //FK


    public MetricasDispositivoRequestDTO() {
    }

    public MetricasDispositivoRequestDTO(MetricasTipo tipo, String valor, String unidade, LocalDateTime dataColeta, Long agenteId) {
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataColeta = dataColeta;
        this.agenteId = agenteId;
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

    public Long getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(Long agenteId) {
        this.agenteId = agenteId;
    }
}
