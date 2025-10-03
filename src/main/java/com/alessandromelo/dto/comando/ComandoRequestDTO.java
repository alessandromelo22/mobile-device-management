package com.alessandromelo.dto.comando;

import com.alessandromelo.enums.ComandoTipo;
import jakarta.validation.constraints.NotBlank;

public class ComandoRequestDTO {

    @NotBlank(message = "O tipo do Comando deve ser informado!")
    private ComandoTipo tipo;
    private String parametros; //Parâmetros adicionais para o comando (por exemplo: motivo do bloqueio, versão da atualização etc.), geralmente em formato JSON ou String
    private Long agenteId;


    public ComandoRequestDTO() {
    }

    public ComandoRequestDTO(ComandoTipo tipo, String parametros, Long agenteId) {
        this.tipo = tipo;
        this.parametros = parametros;
        this.agenteId = agenteId;
    }

    public ComandoTipo getTipo() {
        return tipo;
    }

    public void setTipo(ComandoTipo tipo) {
        this.tipo = tipo;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public Long getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(Long agenteId) {
        this.agenteId = agenteId;
    }
}
