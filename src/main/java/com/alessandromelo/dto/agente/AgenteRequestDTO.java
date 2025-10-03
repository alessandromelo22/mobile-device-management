package com.alessandromelo.dto.agente;


import jakarta.validation.constraints.NotBlank;

public class AgenteRequestDTO {

    @NotBlank(message = "A versão do Agente deve ser informada!")
    private String versao;
    private String log;
    private Long dispositivoId; // FK



    public AgenteRequestDTO() {
    }

    public AgenteRequestDTO(String versao, String log, Long dispositivoId) {
        this.versao = versao;
        this.log = log;
        this.dispositivoId = dispositivoId;
    }


    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Long getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(Long dispositivoId) {
        this.dispositivoId = dispositivoId;
    }
}
