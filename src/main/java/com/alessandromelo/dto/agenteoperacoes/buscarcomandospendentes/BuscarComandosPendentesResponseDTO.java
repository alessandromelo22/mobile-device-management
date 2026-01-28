package com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes;

import com.alessandromelo.dto.comando.ComandoResumoResponseDTO;



import java.util.List;

public class BuscarComandosPendentesResponseDTO {

    private Long agenteId;
    private List<ComandoResumoResponseDTO> comandosResumoResponseDTO;




    public BuscarComandosPendentesResponseDTO() {
    }

    public BuscarComandosPendentesResponseDTO(Long agenteId, List<ComandoResumoResponseDTO> comandosResumoResponseDTO) {
        this.agenteId = agenteId;
        this.comandosResumoResponseDTO = comandosResumoResponseDTO;
    }


    public Long getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(Long agenteId) {
        this.agenteId = agenteId;
    }

    public List<ComandoResumoResponseDTO> getComandosResumoResponseDTO() {
        return comandosResumoResponseDTO;
    }

    public void setComandosResumoResponseDTO(List<ComandoResumoResponseDTO> comandosResumoResponseDTO) {
        this.comandosResumoResponseDTO = comandosResumoResponseDTO;
    }
}
