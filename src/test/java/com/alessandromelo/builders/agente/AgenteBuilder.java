package com.alessandromelo.builders.agente;

import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.enums.AgenteStatus;


import java.time.LocalDateTime;
import java.time.Month;

public class AgenteBuilder {

    private Long id = 1L;
    private String versao = "BETA V1.0.1"; //obrigatorio
    private AgenteStatus status = AgenteStatus.ATIVO;
    private LocalDateTime dataUltimaAtividade;
    private Dispositivo dispositivo; //FK //obrigatorio


    public AgenteBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public AgenteBuilder comVersao(String versao){
        this.versao = versao;
        return this;
    }

    public AgenteBuilder comStatus(AgenteStatus status){
        this.status = status;
        return this;
    }

    public AgenteBuilder comDataUltimaAtividade(LocalDateTime dataUltimaAtividade){
        this.dataUltimaAtividade = dataUltimaAtividade;
        return this;
    }

    public AgenteBuilder comDispositivo(Dispositivo dispositivo){
        this.dispositivo = dispositivo;
        return this;
    }

    public Agente build(){
        Agente agente = new Agente();
        agente.setId(this.id);
        agente.setVersao(this.versao);
        agente.setStatus(this.status);
        agente.setDataUltimaAtividade(this.dataUltimaAtividade);
        agente.setDispositivo(this.dispositivo);

        return agente;
    }

}
