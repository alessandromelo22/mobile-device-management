package com.alessandromelo.builders.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasResponseDTO;
import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class EnviarMetricasResponseDTOBuilder {

    private Long id = 1L;
    private MetricasTipo tipo = MetricasTipo.BATERIA;;
    private String valor = "50";
    private String unidade = "%";
    private LocalDateTime dataColeta = LocalDateTime.of(2026, 3, 11, 8, 25);

    public EnviarMetricasResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public EnviarMetricasResponseDTOBuilder comTipo(MetricasTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public EnviarMetricasResponseDTOBuilder comValor(String valor){
        this.valor = valor;
        return this;
    }

    public EnviarMetricasResponseDTOBuilder comUnidade(String unidade){
        this.unidade = unidade;
        return this;
    }

    public EnviarMetricasResponseDTOBuilder comDataColeta(LocalDateTime dataColeta){
        this.dataColeta = dataColeta;
        return this;
    }


    public EnviarMetricasResponseDTO build(){
        EnviarMetricasResponseDTO responseDTO = new EnviarMetricasResponseDTO();

        responseDTO.setId(this.id);
        responseDTO.setTipo(this.tipo);
        responseDTO.setValor(this.valor);
        responseDTO.setUnidade(this.unidade);
        responseDTO.setDataColeta(this.dataColeta);

        return responseDTO;
    }
}
