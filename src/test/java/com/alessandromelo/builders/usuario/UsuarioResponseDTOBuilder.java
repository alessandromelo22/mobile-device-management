package com.alessandromelo.builders.usuario;

import com.alessandromelo.dto.departamento.DepartamentoResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;

public class UsuarioResponseDTOBuilder {

    private Long id = 1L;
    private String nome = "Jorge da Silva";
    private String email = "jorginds69@gmail.com";
    private String matricula = "7001";
    private String cargo = "Analista de RH";
    private Boolean ativo = true;
    private DepartamentoResumoResponseDTO departamentoResumoResponseDTO;

    public UsuarioResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public UsuarioResponseDTOBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioResponseDTOBuilder comEmail(String email){
        this.email = email;
        return this;
    }

    public UsuarioResponseDTOBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }


    public UsuarioResponseDTOBuilder comCargo(String cargo){
        this.cargo = cargo;
        return this;
    }

    public UsuarioResponseDTOBuilder estaAtivo(Boolean ativo){
        this.ativo = ativo;
        return this;
    }

    public UsuarioResponseDTOBuilder comDepartamentoResumoResponseDTO(DepartamentoResumoResponseDTO departamentoResumoResponseDTO){
        this.departamentoResumoResponseDTO = departamentoResumoResponseDTO;
        return this;
    }

    public UsuarioResponseDTO build(){
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setNome(this.nome);
        responseDTO.setEmail(this.email);
        responseDTO.setMatricula(this.matricula);
        responseDTO.setCargo(this.cargo);
        responseDTO.setAtivo(this.ativo);
        responseDTO.setDepartamentoResumoResponseDTO(this.departamentoResumoResponseDTO);

        return responseDTO;
    }
}
