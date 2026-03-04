package com.alessandromelo.builders.dispositivo;

import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.enums.DispositivoStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DispositivoRequestDTOBuilder {

    private String modelo = "Aspire 5"; //Obrigatorio
    private String marca = "Acer"; //Obrigatorio
    private String numeroSerie = "6977a67dey0"; //Obrigatorio
    private String sistemaOperacional = "Windows";
    private String versaoSO = "11 25H2";
    private DispositivoStatus status = DispositivoStatus.ATIVO;
    private LocalDate dataAquisicao = LocalDate.of(2025, 10, 22);
    private LocalDateTime dataUltimaAtualizacao = LocalDateTime.of(2025, Month.NOVEMBER, 20, 10, 30);
    private String observacoes = "";
    private Long usuarioId;


    public DispositivoRequestDTOBuilder comModelo (String modelo){
        this.modelo = modelo;
        return this;
    }

    public DispositivoRequestDTOBuilder comMarca (String marca){
        this.marca = marca;
        return this;
    }

    public DispositivoRequestDTOBuilder comNumeroSerie (String numeroSerie){
        this.numeroSerie = numeroSerie;
        return this;
    }

    public DispositivoRequestDTOBuilder comSistemaOperacional (String sistemaOperacional){
        this.sistemaOperacional = sistemaOperacional;
        return this;
    }

    public DispositivoRequestDTOBuilder comVersaoSO (String versaoSO){
        this.versaoSO = versaoSO;
        return this;
    }

    public DispositivoRequestDTOBuilder comStatus (DispositivoStatus status){
        this.status = status;
        return this;
    }

    public DispositivoRequestDTOBuilder comDataAquisicao (LocalDate dataAquisicao){
        this.dataAquisicao = dataAquisicao;
        return this;
    }

    public DispositivoRequestDTOBuilder comDataUltimaAtualizacao (LocalDateTime dataUltimaAtualizacao){
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        return this;
    }

    public DispositivoRequestDTOBuilder comObservacoes (String observacoes){
        this.observacoes = observacoes;
        return this;
    }

    public DispositivoRequestDTOBuilder comUsuarioId (Long usuarioId){
        this.usuarioId = usuarioId;
        return this;
    }


    public DispositivoRequestDTO build (){
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTO();
        requestDTO.setModelo(this.modelo);
        requestDTO.setMarca(this.marca);
        requestDTO.setNumeroSerie(this.numeroSerie);
        requestDTO.setSistemaOperacional(this.sistemaOperacional);
        requestDTO.setVersaoSO(this.versaoSO);
        requestDTO.setStatus(this.status);
        requestDTO.setDataAquisicao(this.dataAquisicao);
        requestDTO.setDataUltimaAtualizacao(this.dataUltimaAtualizacao);
        requestDTO.setObservacoes(this.observacoes);
        requestDTO.setUsuarioId(this.usuarioId);

        return requestDTO;
    }
}
