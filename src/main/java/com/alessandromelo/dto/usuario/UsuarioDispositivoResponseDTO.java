package com.alessandromelo.dto.usuario;

import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;

public class UsuarioDispositivoResponseDTO {

    private Long usuarioId;
    private String nome;
    private String matricula;
    private DispositivoResumoResponseDTO dispositivoResumoResponseDTO;


    public UsuarioDispositivoResponseDTO() {
    }

    public UsuarioDispositivoResponseDTO(Long usuarioId, String nome, String matricula, DispositivoResumoResponseDTO dispositivoResumoResponseDTO) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.matricula = matricula;
        this.dispositivoResumoResponseDTO = dispositivoResumoResponseDTO;
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

    public DispositivoResumoResponseDTO getDispositivoResumoResponseDTO() {
        return dispositivoResumoResponseDTO;
    }

    public void setDispositivoResumoResponseDTO(DispositivoResumoResponseDTO dispositivoResumoResponseDTO) {
        this.dispositivoResumoResponseDTO = dispositivoResumoResponseDTO;
    }
}
