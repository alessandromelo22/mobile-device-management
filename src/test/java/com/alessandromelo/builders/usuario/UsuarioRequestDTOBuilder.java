package com.alessandromelo.builders.usuario;


import com.alessandromelo.dto.usuario.UsuarioRequestDTO;

public class UsuarioRequestDTOBuilder {

    private String nome = "Jorge da Silva"; //Obrigatorio
    private String email = "jorginds69@gmail.com"; //Obrigatorio
    private String matricula = "7001"; //Obrigatorio
    private String cargo = "Analista de RH";
    private Boolean ativo = true;
    private Long departamentoId;


    public UsuarioRequestDTOBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioRequestDTOBuilder comEmail(String email){
        this.email = email;
        return this;
    }

    public UsuarioRequestDTOBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }


    public UsuarioRequestDTOBuilder comCargo(String cargo){
        this.cargo = cargo;
        return this;
    }

    public UsuarioRequestDTOBuilder estaAtivo(Boolean ativo){
        this.ativo = ativo;
        return this;
    }

    public UsuarioRequestDTOBuilder comDepartamentoId(Long departamentoId){
        this.departamentoId = departamentoId;
        return this;
    }

    public UsuarioRequestDTO build(){
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome(this.nome);
        requestDTO.setEmail(this.email);
        requestDTO.setMatricula(this.matricula);
        requestDTO.setCargo(this.cargo);
        requestDTO.setAtivo(this.ativo);
        requestDTO.setDepartamentoId(this.departamentoId);

        return requestDTO;
    }
}
