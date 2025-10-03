package com.alessandromelo.mapper.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.entity.Agente;
import org.springframework.stereotype.Component;

@Component
public class AtualizarStatusMapper {

    public AtualizarStatusResponseDTO toResponseDTO(Agente agente){

        AtualizarStatusResponseDTO atualizarStatusResponseDTO = new AtualizarStatusResponseDTO();

        atualizarStatusResponseDTO.setId(agente.getId());
        atualizarStatusResponseDTO.setVersao(agente.getVersao());
        atualizarStatusResponseDTO.setStatus(agente.getStatus());
        atualizarStatusResponseDTO.setDataUltimaAtividade(agente.getDataUltimaAtividade());

        return null;
    }

}
