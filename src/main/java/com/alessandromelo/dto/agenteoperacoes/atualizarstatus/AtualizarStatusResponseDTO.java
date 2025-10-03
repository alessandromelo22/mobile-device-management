package com.alessandromelo.dto.agenteoperacoes.atualizarstatus;

import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;

public class AtualizarStatusResponseDTO {

    private Long id;
    private String versao;
    private AgenteStatus status;
    private LocalDateTime dataUltimaAtividade;



    public AtualizarStatusResponseDTO() {
    }

    public AtualizarStatusResponseDTO(Long id, String versao, AgenteStatus status, LocalDateTime dataUltimaAtividade) {
        this.id = id;
        this.versao = versao;
        this.status = status;
        this.dataUltimaAtividade = dataUltimaAtividade;
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
}
