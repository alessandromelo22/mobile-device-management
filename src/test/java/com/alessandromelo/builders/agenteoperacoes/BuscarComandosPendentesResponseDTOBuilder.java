package com.alessandromelo.builders.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.dto.comando.ComandoResumoResponseDTO;

import java.util.List;

public class BuscarComandosPendentesResponseDTOBuilder {

    private Long agenteId = 1L;
    private List<ComandoResumoResponseDTO> comandosResumoResponseDTO;

    public BuscarComandosPendentesResponseDTOBuilder comAgenteId(Long agenteId){
        this.agenteId = agenteId;
        return this;
    }

    public BuscarComandosPendentesResponseDTOBuilder comComandosResumoResponseDTO(List<ComandoResumoResponseDTO> comandosResumoResponseDTO){
        this.comandosResumoResponseDTO = comandosResumoResponseDTO;
        return this;
    }

    public BuscarComandosPendentesResponseDTO build(){
        BuscarComandosPendentesResponseDTO responseDTO = new BuscarComandosPendentesResponseDTO();
        responseDTO.setAgenteId(this.agenteId);
        responseDTO.setComandosResumoResponseDTO(this.comandosResumoResponseDTO);

        return  responseDTO;
    }
}
