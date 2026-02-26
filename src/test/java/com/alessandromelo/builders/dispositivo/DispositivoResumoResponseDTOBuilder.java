package com.alessandromelo.builders.dispositivo;

import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;

public class DispositivoResumoResponseDTOBuilder {

    private Long id = 1L;
    private String modelo = "Aspire 5";
    private DispositivoStatus status = DispositivoStatus.ATIVO;

    public DispositivoResumoResponseDTOBuilder comId (Long id){
        this.id = id;
        return this;
    }

    public DispositivoResumoResponseDTOBuilder comModelo (String modelo){
        this.modelo = modelo;
        return this;
    }

    public DispositivoResumoResponseDTOBuilder comStatus (DispositivoStatus status){
        this.status = status;
        return this;
    }

    public DispositivoResumoResponseDTO build (){
        DispositivoResumoResponseDTO resumoResponseDTO = new DispositivoResumoResponseDTO();
        resumoResponseDTO.setId(this.id);
        resumoResponseDTO.setModelo(this.modelo);
        resumoResponseDTO.setStatus(this.status);

        return resumoResponseDTO;
    }

}
