package com.alessandromelo.dto.agenteoperacoes.enviarmetricas;

import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class EnviarMetricasRequestDTO {

    private MetricasTipo tipo;
    private String valor;
    private String unidade;
    private LocalDateTime dataColeta;


}
