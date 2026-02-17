package com.alessandromelo.builders.departamento;

import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;

public class DepartamentoRequestDTOBuilder {

    private String nome = "Recursos Humanos";
    private String sigla = "RH";



    public DepartamentoRequestDTOBuilder comNome (String nome){
        this.nome = nome;
        return this;
    }

    public DepartamentoRequestDTOBuilder comSigla (String sigla){
        this.sigla = sigla;
        return this;
    }


    public DepartamentoRequestDTO build () {
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTO();
        requestDTO.setNome(this.nome);
        requestDTO.setSigla(this.sigla);

        return requestDTO;
    }
}
