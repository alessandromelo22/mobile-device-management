package com.alessandromelo.builders.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasRequestDTO;
import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class EnviarMetricasRequestDTOBuilder {


    private MetricasTipo tipo = MetricasTipo.BATERIA;
    private String valor = "50";
    private String unidade = "%";
    private LocalDateTime dataColeta = LocalDateTime.of(2026, 3, 11, 8, 25);

    public EnviarMetricasRequestDTOBuilder comTipo(MetricasTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public EnviarMetricasRequestDTOBuilder comValor(String valor){
        this.valor = valor;
        return this;
    }

    public EnviarMetricasRequestDTOBuilder comUnidade(String unidade){
        this.unidade = unidade;
        return this;
    }

    public EnviarMetricasRequestDTOBuilder comDataColeta(LocalDateTime dataColeta){
        this.dataColeta = dataColeta;
        return this;
    }

    public EnviarMetricasRequestDTO build(){
        EnviarMetricasRequestDTO requestDTO = new EnviarMetricasRequestDTO();
        requestDTO.setTipo(this.tipo);
        requestDTO.setValor(this.valor);
        requestDTO.setUnidade(this.unidade);
        requestDTO.setDataColeta(this.dataColeta);

        return requestDTO;
    }
}
