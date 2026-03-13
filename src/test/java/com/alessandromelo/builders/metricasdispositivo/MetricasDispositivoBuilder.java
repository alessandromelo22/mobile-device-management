package com.alessandromelo.builders.metricasdispositivo;

import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.MetricasDispositivo;
import com.alessandromelo.enums.MetricasTipo;

import java.time.LocalDateTime;

public class MetricasDispositivoBuilder {


    private Long id = 1L;
    private MetricasTipo tipo = MetricasTipo.BATERIA; //Obrigatorio
    private String valor = "50"; //Obrigatorio
    private String unidade = "%"; //Obrigatorio
    private LocalDateTime dataColeta = LocalDateTime.of(2026, 3, 11, 8, 25); //Obrigatorio
    private Agente agente;

    public MetricasDispositivoBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public MetricasDispositivoBuilder comTipo(MetricasTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public MetricasDispositivoBuilder comValor(String valor){
        this.valor = valor;
        return this;
    }

    public MetricasDispositivoBuilder comUnidade(String unidade){
        this.unidade = unidade;
        return this;
    }

    public MetricasDispositivoBuilder comDataColeta(LocalDateTime dataColeta){
        this.dataColeta = dataColeta;
        return this;
    }

    public MetricasDispositivoBuilder comAgente(Agente agente){
        this.agente = agente;
        return this;
    }


    public MetricasDispositivo build(){
        MetricasDispositivo metricasDispositivo = new MetricasDispositivo();

        metricasDispositivo.setId(this.id);
        metricasDispositivo.setTipo(this.tipo);
        metricasDispositivo.setValor(this.valor);
        metricasDispositivo.setUnidade(this.unidade);
        metricasDispositivo.setDataColeta(this.dataColeta);
        metricasDispositivo.setAgente(this.agente);

        return metricasDispositivo;
    }
}
