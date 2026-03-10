package com.alessandromelo.dto.agente;

import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;

public class AgenteResponseDTO {

    private Long id;
    private String versao;
    private AgenteStatus status;
    private LocalDateTime dataUltimaAtividade;
    private DispositivoResumoResponseDTO dispositivoResumoResponseDTO; // FK

    public AgenteResponseDTO() {
    }

    public AgenteResponseDTO(Long id, String versao, AgenteStatus status, LocalDateTime dataUltimaAtividade, DispositivoResumoResponseDTO dispositivoResumoResponseDTO) {
        this.id = id;
        this.versao = versao;
        this.status = status;
        this.dataUltimaAtividade = dataUltimaAtividade;
        this.dispositivoResumoResponseDTO = dispositivoResumoResponseDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public AgenteStatus getStatus() {
        return status;
    }

    public void setStatus(AgenteStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataUltimaAtividade() {
        return dataUltimaAtividade;
    }

    public void setDataUltimaAtividade(LocalDateTime dataUltimaAtividade) {
        this.dataUltimaAtividade = dataUltimaAtividade;
    }

    public DispositivoResumoResponseDTO getDispositivoResumoResponseDTO() {
        return dispositivoResumoResponseDTO;
    }

    public void setDispositivoResumoResponseDTO(DispositivoResumoResponseDTO dispositivoResumoResponseDTO) {
        this.dispositivoResumoResponseDTO = dispositivoResumoResponseDTO;
    }
}
