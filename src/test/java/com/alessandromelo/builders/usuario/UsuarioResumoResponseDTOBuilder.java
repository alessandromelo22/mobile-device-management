package com.alessandromelo.builders.usuario;

import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;

public class UsuarioResumoResponseDTOBuilder {

    private Long id = 1L;
    private String nome = "Jorge da Silva"; //Obrigatorio
    private String matricula = "7001"; //Obrigatorio

    public UsuarioResumoResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public UsuarioResumoResponseDTOBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioResumoResponseDTOBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }


    public UsuarioResumoResponseDTO build() {
        UsuarioResumoResponseDTO resumoResponseDTO = new UsuarioResumoResponseDTO();
        resumoResponseDTO.setId(this.id);
        resumoResponseDTO.setNome(this.nome);
        resumoResponseDTO.setMatricula(this.matricula);

        return resumoResponseDTO;
    }
}
