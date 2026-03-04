package com.alessandromelo.builders.usuario;

import com.alessandromelo.dto.departamento.DepartamentoResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDepartamentoResponseDTO;

public class UsuarioDepartamentoResponseDTOBuilder {
    private Long usuarioId = 1L;
    private String nome = "Jorge da Silva"; //Obrigatorio
    private String matricula = "7001"; //Obrigatorio
    private DepartamentoResumoResponseDTO departamentoResumoResponseDTO;

    public UsuarioDepartamentoResponseDTOBuilder comUsuarioId(Long usuarioId){
        this.usuarioId = usuarioId;
        return this;
    }

    public UsuarioDepartamentoResponseDTOBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioDepartamentoResponseDTOBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }

    public UsuarioDepartamentoResponseDTOBuilder comDepartamentoResumoResponseDTO (DepartamentoResumoResponseDTO departamentoResumoResponseDTO){
        this.departamentoResumoResponseDTO = departamentoResumoResponseDTO;
        return this;
    }

    public UsuarioDepartamentoResponseDTO build(){
        UsuarioDepartamentoResponseDTO usuarioDepartamentoResponseDTO = new UsuarioDepartamentoResponseDTO();
        usuarioDepartamentoResponseDTO.setUsuarioId(this.usuarioId);
        usuarioDepartamentoResponseDTO.setNome(this.nome);
        usuarioDepartamentoResponseDTO.setMatricula(this.matricula);
        usuarioDepartamentoResponseDTO.setDepartamentoResumoResponseDTO(this.departamentoResumoResponseDTO);

        return usuarioDepartamentoResponseDTO;
    }
}
