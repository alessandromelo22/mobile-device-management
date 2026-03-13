package com.alessandromelo.builders.comando;

import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.enums.ComandoTipo;

import java.time.LocalDateTime;

public class ComandoBuilder {

    private Long id = 1L;
    private ComandoTipo tipo = ComandoTipo.BLOQUEAR;
    private ComandoStatus status = ComandoStatus.PENDENTE;
    private String parametros = "";
    private LocalDateTime dataCriacao = LocalDateTime.of(2026, 3, 10, 10, 22);
    private LocalDateTime dataExecucao = LocalDateTime.of(2026, 3, 11, 8, 25);
    private Agente agente; // FK


    public ComandoBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public ComandoBuilder comTipo(ComandoTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public ComandoBuilder comStatus(ComandoStatus status){
        this.status = status;
        return this;
    }

    public ComandoBuilder comParametros(String parametros){
        this.parametros = parametros;
        return this;
    }

    public ComandoBuilder comDataCriacao(LocalDateTime dataCriacao){
        this.dataCriacao = dataCriacao;
        return this;
    }

    public ComandoBuilder comDataExecucao(LocalDateTime dataExecucao){
        this.dataExecucao = dataExecucao;
        return this;
    }

    public ComandoBuilder comAgente(Agente agente){
        this.agente = agente;
        return this;
    }

    public Comando build(){
        Comando comando = new Comando();
        comando.setId(this.id);
        comando.setTipo(this.tipo);
        comando.setStatus(this.status);
        comando.setParametros(this.parametros);
        comando.setDataCriacao(this.dataCriacao);
        comando.setDataExecucao(this.dataExecucao);
        comando.setAgente(this.agente);

        return comando;
    }
}
