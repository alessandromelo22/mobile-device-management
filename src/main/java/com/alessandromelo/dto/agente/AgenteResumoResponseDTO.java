package com.alessandromelo.dto.agente;

import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;

public class AgenteResumoResponseDTO {

    private Long id;
    private AgenteStatus status;
    private LocalDateTime dataUltimaAtividade;

    public AgenteResumoResponseDTO() {
    }

    public AgenteResumoResponseDTO(Long id, AgenteStatus status, LocalDateTime dataUltimaAtividade) {
        this.id = id;
        this.status = status;
        this.dataUltimaAtividade = dataUltimaAtividade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
