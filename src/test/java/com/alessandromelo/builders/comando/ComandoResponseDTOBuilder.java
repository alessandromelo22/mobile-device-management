package com.alessandromelo.builders.comando;

import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.enums.ComandoTipo;

import java.time.LocalDateTime;

public class ComandoResponseDTOBuilder {

    private Long id = 1L;
    private ComandoTipo tipo = ComandoTipo.BLOQUEAR;
    private ComandoStatus status = ComandoStatus.PENDENTE;
    private String parametros = "";
    private LocalDateTime dataCriacao = LocalDateTime.of(2026, 3, 10, 10, 22);
    private LocalDateTime dataExecucao = LocalDateTime.of(2026, 3, 11, 8, 25);
    private Long agenteId;

    public ComandoResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public ComandoResponseDTOBuilder comTipo(ComandoTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public ComandoResponseDTOBuilder comStatus(ComandoStatus status){
        this.status = status;
        return this;
    }

    public ComandoResponseDTOBuilder comParametros(String parametros){
        this.parametros = parametros;
        return this;
    }

    public ComandoResponseDTOBuilder comDataCriacao(LocalDateTime dataCriacao){
        this.dataCriacao = dataCriacao;
        return this;
    }

    public ComandoResponseDTOBuilder comDataExecucao(LocalDateTime dataExecucao){
        this.dataExecucao = dataExecucao;
        return this;
    }

    public ComandoResponseDTOBuilder comAgenteId(Long agenteId){
        this.agenteId = agenteId;
        return this;
    }


    public ComandoResponseDTO build(){
        ComandoResponseDTO responseDTO = new ComandoResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setTipo(this.tipo);
        responseDTO.setStatus(this.status);
        responseDTO.setParametros(this.parametros);
        responseDTO.setDataCriacao(this.dataCriacao);
        responseDTO.setDataExecucao(this.dataExecucao);
        responseDTO.setAgenteId(this.agenteId);

        return responseDTO;
    }
}
