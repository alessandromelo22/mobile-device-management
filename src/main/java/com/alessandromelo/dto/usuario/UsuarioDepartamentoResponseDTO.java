package com.alessandromelo.dto.usuario;

import com.alessandromelo.dto.departamento.DepartamentoResumoResponseDTO;

public class UsuarioDepartamentoResponseDTO {

    private Long usuarioId;
    private String nome;
    private String matricula;
    private DepartamentoResumoResponseDTO departamentoResumoResponseDTO;


    public UsuarioDepartamentoResponseDTO() {
    }

    public UsuarioDepartamentoResponseDTO(Long usuarioId, String nome, String matricula, DepartamentoResumoResponseDTO departamentoResumoResponseDTO) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.matricula = matricula;
        this.departamentoResumoResponseDTO = departamentoResumoResponseDTO;
    }


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public DepartamentoResumoResponseDTO getDepartamentoResumoResponseDTO() {
        return departamentoResumoResponseDTO;
    }

    public void setDepartamentoResumoResponseDTO(DepartamentoResumoResponseDTO departamentoResumoResponseDTO) {
        this.departamentoResumoResponseDTO = departamentoResumoResponseDTO;
    }
}
