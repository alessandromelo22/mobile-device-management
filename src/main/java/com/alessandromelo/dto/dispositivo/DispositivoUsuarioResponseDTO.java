package com.alessandromelo.dto.dispositivo;

import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;

public class DispositivoUsuarioResponseDTO {

    private Long dispositivoId;
    private String modelo;
    private DispositivoStatus status;
    private UsuarioResumoResponseDTO usuarioResumoResponseDTO;

    public DispositivoUsuarioResponseDTO() {
    }

    public DispositivoUsuarioResponseDTO(Long dispositivoId, String modelo, DispositivoStatus status, UsuarioResumoResponseDTO usuarioResumoResponseDTO) {
        this.dispositivoId = dispositivoId;
        this.modelo = modelo;
        this.status = status;
        this.usuarioResumoResponseDTO = usuarioResumoResponseDTO;
    }

    public Long getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(Long dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public DispositivoStatus getStatus() {
        return status;
    }

    public void setStatus(DispositivoStatus status) {
        this.status = status;
    }

    public UsuarioResumoResponseDTO getUsuarioResumoResponseDTO() {
        return usuarioResumoResponseDTO;
    }

    public void setUsuarioResumoResponseDTO(UsuarioResumoResponseDTO usuarioResumoResponseDTO) {
        this.usuarioResumoResponseDTO = usuarioResumoResponseDTO;
    }
}
