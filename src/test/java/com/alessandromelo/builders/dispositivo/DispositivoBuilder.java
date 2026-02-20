package com.alessandromelo.builders.dispositivo;

import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.enums.DispositivoStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DispositivoBuilder {


    private Long id = 1L;
    private String modelo = "Aspire 5"; //Obrigatorio
    private String marca = "Acer"; //Obrigatorio
    private String numeroSerie = "6977a67dey0"; //Obrigatorio
    private String sistemaOperacional = "Windows";
    private String versaoSO = "11 25H2";
    private DispositivoStatus status = DispositivoStatus.ATIVO;
    private LocalDate dataAquisicao = LocalDate.of(2025, 10, 22);
    private LocalDateTime dataUltimaAtualizacao = LocalDateTime.of(2025, Month.NOVEMBER, 20, 10, 30);
    private String observacoes = "";
    private Usuario usuario;

    public DispositivoBuilder comId (Long id){
        this.id = id;
        return this;
    }

    public DispositivoBuilder comModelo (String modelo){
        this.modelo = modelo;
        return this;
    }

    public DispositivoBuilder comMarca (String marca){
        this.marca = marca;
        return this;
    }

    public DispositivoBuilder comNumeroSerie (String numeroSerie){
        this.numeroSerie = numeroSerie;
        return this;
    }

    public DispositivoBuilder comSistemaOperacional (String sistemaOperacional){
        this.sistemaOperacional = sistemaOperacional;
        return this;
    }

    public DispositivoBuilder comVersaoSO (String versaoSO){
        this.versaoSO = versaoSO;
        return this;
    }

    public DispositivoBuilder comStatus (DispositivoStatus status){
        this.status = status;
        return this;
    }

    public DispositivoBuilder comDataAquisicao (LocalDate dataAquisicao){
        this.dataAquisicao = dataAquisicao;
        return this;
    }

    public DispositivoBuilder comDataUltimaAtualizacao (LocalDateTime dataUltimaAtualizacao){
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        return this;
    }

    public DispositivoBuilder comObservacoes (String observacoes){
        this.observacoes = observacoes;
        return this;
    }

    public DispositivoBuilder comUsuario (Usuario usuario){
        this.usuario = usuario;
        return this;
    }


    public Dispositivo build (){
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(this.id);
        dispositivo.setModelo(this.modelo);
        dispositivo.setMarca(this.marca);
        dispositivo.setNumeroSerie(this.numeroSerie);
        dispositivo.setSistemaOperacional(this.sistemaOperacional);
        dispositivo.setVersaoSO(this.versaoSO);
        dispositivo.setStatus(this.status);
        dispositivo.setDataAquisicao(this.dataAquisicao);
        dispositivo.setDataUltimaAtualizacao(this.dataUltimaAtualizacao);
        dispositivo.setObservacoes(this.observacoes);
        dispositivo.setUsuario(this.usuario);

        return dispositivo;
    }

}
