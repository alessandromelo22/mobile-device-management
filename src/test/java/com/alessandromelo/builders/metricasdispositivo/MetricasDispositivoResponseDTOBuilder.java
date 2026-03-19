package com.alessandromelo.builders.metricasdispositivo;

import com.alessandromelo.dto.metricasdispositivo.MetricasDispositivoResponseDTO;
import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class MetricasDispositivoResponseDTOBuilder {

    private Long id = 1L;
    private MetricasTipo tipo = MetricasTipo.BATERIA; //Obrigatorio
    private String valor = "50"; //Obrigatorio
    private String unidade = "%"; //Obrigatorio
    private LocalDateTime dataColeta = LocalDateTime.of(2026, 3, 11, 8, 25);
    private Long agenteId;


    public MetricasDispositivoResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public MetricasDispositivoResponseDTOBuilder comTipo(MetricasTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public MetricasDispositivoResponseDTOBuilder comValor(String valor){
        this.valor = valor;
        return this;
    }

    public MetricasDispositivoResponseDTOBuilder comUnidade(String unidade){
        this.unidade = unidade;
        return this;
    }

    public MetricasDispositivoResponseDTOBuilder comDataColeta(LocalDateTime dataColeta){
        this.dataColeta = dataColeta;
        return this;
    }

    public MetricasDispositivoResponseDTOBuilder comAgenteId(Long agenteId){
        this.agenteId = agenteId;
        return this;
    }


    public MetricasDispositivoResponseDTO build(){
        MetricasDispositivoResponseDTO responseDTO = new MetricasDispositivoResponseDTO();

        responseDTO.setId(this.id);
        responseDTO.setTipo(this.tipo);
        responseDTO.setValor(this.valor);
        responseDTO.setUnidade(this.unidade);
        responseDTO.setDataColeta(this.dataColeta);
        responseDTO.setAgenteId(this.agenteId);

        return responseDTO;
    }
}
