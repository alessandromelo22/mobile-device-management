package com.alessandromelo.builders.agente;

import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;

public class AgenteResumoResponseDTOBuilder {

    private Long id = 1L;
    private AgenteStatus status = AgenteStatus.ATIVO;
    private LocalDateTime dataUltimaAtividade;

    public AgenteResumoResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public AgenteResumoResponseDTOBuilder comStatus(AgenteStatus status){
        this.status = status;
        return this;
    }

    public AgenteResumoResponseDTOBuilder comDataUltimaAtividade(LocalDateTime dataUltimaAtividade){
        this.dataUltimaAtividade = dataUltimaAtividade;
        return this;
    }

    public AgenteResumoResponseDTO build(){
        AgenteResumoResponseDTO responseDTO = new AgenteResumoResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setStatus(this.status);
        responseDTO.setDataUltimaAtividade(this.dataUltimaAtividade);

        return responseDTO;
    }
}
