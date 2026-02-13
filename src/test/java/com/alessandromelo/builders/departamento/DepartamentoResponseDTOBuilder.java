package com.alessandromelo.builders.departamento;

import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;

public class DepartamentoResponseDTOBuilder {

    private Long id = 1L;
    private String nome = "Recursos Humanos";
    private String sigla = "RH";


    public DepartamentoResponseDTOBuilder comId (Long id){
        this.id = id;
        return this;
    }

    public DepartamentoResponseDTOBuilder comNome (String nome){
        this.nome = nome;
        return this;
    }

    public DepartamentoResponseDTOBuilder comSigla (String sigla){
        this.sigla = sigla;
        return this;
    }


    public DepartamentoResponseDTO build () {
        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setNome(this.nome);
        responseDTO.setSigla(this.sigla);

        return responseDTO;
    }
}
