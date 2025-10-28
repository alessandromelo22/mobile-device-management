package com.alessandromelo.mapper.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.entity.Agente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtualizarStatusMapper {

    //Entity -> AtualizarStatusResponseDTO
    AtualizarStatusResponseDTO toResponseDTO(Agente agente);
}
