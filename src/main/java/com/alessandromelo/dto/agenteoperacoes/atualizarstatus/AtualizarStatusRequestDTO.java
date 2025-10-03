package com.alessandromelo.dto.agenteoperacoes.atualizarstatus;

import jakarta.validation.constraints.NotBlank;

public class AtualizarStatusRequestDTO {
    @NotBlank(message = "O Agente deve informar a versão!")
    private String versao;



    public AtualizarStatusRequestDTO() {
    }

    public AtualizarStatusRequestDTO(String versao) {
        this.versao = versao;
    }


    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

}
