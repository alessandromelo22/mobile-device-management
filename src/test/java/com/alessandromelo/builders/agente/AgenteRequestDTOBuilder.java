package com.alessandromelo.builders.agente;


import com.alessandromelo.dto.agente.AgenteRequestDTO;

public class AgenteRequestDTOBuilder {

    private String versao = "BETA V1.0.1"; //obrigatorio
    private Long dispositivoId;


    public AgenteRequestDTOBuilder comVersao(String versao){
        this.versao = versao;
        return this;
    }

    public AgenteRequestDTOBuilder comDispositivoId(Long dispositivoId){
        this.dispositivoId = dispositivoId;
        return this;
    }


    public AgenteRequestDTO build(){
        AgenteRequestDTO requestDTO = new AgenteRequestDTO();
        requestDTO.setVersao(this.versao);
        requestDTO.setDispositivoId(this.dispositivoId);

        return requestDTO;
    }
}
