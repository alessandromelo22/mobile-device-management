package com.alessandromelo.builders.comando;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.enums.ComandoTipo;

public class ComandoRequestDTOBuilder {

    private ComandoTipo tipo = ComandoTipo.BLOQUEAR;
    private String parametros = "";
    private Long agenteId;



    public ComandoRequestDTOBuilder comTipo(ComandoTipo tipo){
        this.tipo = tipo;
        return this;
    }

    public ComandoRequestDTOBuilder comParametros(String parametros){
        this.parametros = parametros;
        return this;
    }

    public ComandoRequestDTOBuilder comAgenteId(Long agenteId){
        this.agenteId = agenteId;
        return this;
    }

    public ComandoRequestDTO build() {
        ComandoRequestDTO requestDTO = new ComandoRequestDTO();
        requestDTO.setTipo(this.tipo);
        requestDTO.setParametros(this.parametros);
        requestDTO.setAgenteId(this.agenteId);

        return requestDTO;
    }
}
