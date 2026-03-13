package com.alessandromelo.builders.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;

public class AtualizarStatusRequestDTOBuilder {

    private String versao = "BETA V1.0.1";


    public AtualizarStatusRequestDTOBuilder comVersao(String versao){
        this.versao = versao;
        return this;
    }

    public AtualizarStatusRequestDTO build(){
        AtualizarStatusRequestDTO requestDTO = new AtualizarStatusRequestDTO();
        requestDTO.setVersao(this.versao);

        return requestDTO;
    }
}
