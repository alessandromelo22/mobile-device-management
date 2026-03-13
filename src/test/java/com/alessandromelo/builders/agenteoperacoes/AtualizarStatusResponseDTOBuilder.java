package com.alessandromelo.builders.agenteoperacoes;

import com.alessandromelo.builders.agente.AgenteResponseDTOBuilder;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;

public class AtualizarStatusResponseDTOBuilder {

    private Long id = 1L;
    private String versao = "BETA V1.0.1"; //obrigatorio
    private AgenteStatus status = AgenteStatus.ATIVO;
    private LocalDateTime dataUltimaAtividade;

    public AtualizarStatusResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public AtualizarStatusResponseDTOBuilder comVersao(String versao){
        this.versao = versao;
        return this;
    }

    public AtualizarStatusResponseDTOBuilder comStatus(AgenteStatus status){
        this.status = status;
        return this;
    }

    public AtualizarStatusResponseDTOBuilder comDataUltimaAtividade(LocalDateTime dataUltimaAtividade){
        this.dataUltimaAtividade = dataUltimaAtividade;
        return this;
    }

    public AtualizarStatusResponseDTO build(){
        AtualizarStatusResponseDTO responseDTO = new AtualizarStatusResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setVersao(this.versao);
        responseDTO.setStatus(this.status);
        responseDTO.setDataUltimaAtividade(this.dataUltimaAtividade);

        return responseDTO;
    }
}
