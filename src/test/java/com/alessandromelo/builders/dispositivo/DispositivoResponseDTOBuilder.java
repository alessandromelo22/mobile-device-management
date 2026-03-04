package com.alessandromelo.builders.dispositivo;

import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class DispositivoResponseDTOBuilder {

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
    private UsuarioResumoResponseDTO usuarioResumoResponseDTO; //(FK)

    public DispositivoResponseDTOBuilder comId (Long id){
        this.id = id;
        return this;
    }

    public DispositivoResponseDTOBuilder comModelo (String modelo){
        this.modelo = modelo;
        return this;
    }

    public DispositivoResponseDTOBuilder comMarca (String marca){
        this.marca = marca;
        return this;
    }

    public DispositivoResponseDTOBuilder comNumeroSerie (String numeroSerie){
        this.numeroSerie = numeroSerie;
        return this;
    }

    public DispositivoResponseDTOBuilder comSistemaOperacional (String sistemaOperacional){
        this.sistemaOperacional = sistemaOperacional;
        return this;
    }

    public DispositivoResponseDTOBuilder comVersaoSO (String versaoSO){
        this.versaoSO = versaoSO;
        return this;
    }

    public DispositivoResponseDTOBuilder comStatus (DispositivoStatus status){
        this.status = status;
        return this;
    }

    public DispositivoResponseDTOBuilder comDataAquisicao (LocalDate dataAquisicao){
        this.dataAquisicao = dataAquisicao;
        return this;
    }

    public DispositivoResponseDTOBuilder comDataUltimaAtualizacao (LocalDateTime dataUltimaAtualizacao){
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        return this;
    }

    public DispositivoResponseDTOBuilder comObservacoes (String observacoes){
        this.observacoes = observacoes;
        return this;
    }

    public DispositivoResponseDTOBuilder comUsuarioResumoResponseDTO (UsuarioResumoResponseDTO usuarioResumoResponseDTO){
        this.usuarioResumoResponseDTO = usuarioResumoResponseDTO;
        return this;
    }


    public DispositivoResponseDTO build (){
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setModelo(this.modelo);
        responseDTO.setMarca(this.marca);
        responseDTO.setNumeroSerie(this.numeroSerie);
        responseDTO.setSistemaOperacional(this.sistemaOperacional);
        responseDTO.setVersaoSO(this.versaoSO);
        responseDTO.setStatus(this.status);
        responseDTO.setDataAquisicao(this.dataAquisicao);
        responseDTO.setDataUltimaAtualizacao(this.dataUltimaAtualizacao);
        responseDTO.setObservacoes(this.observacoes);
        responseDTO.setUsuarioResumoResponseDTO(this.usuarioResumoResponseDTO);

        return responseDTO;
    }
}
