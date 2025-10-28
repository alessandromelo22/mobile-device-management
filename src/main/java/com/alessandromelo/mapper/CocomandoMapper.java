package com.alessandromelo.mapper;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.dto.comando.ComandoResumoResponseDTO;
import com.alessandromelo.entity.Comando;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CocomandoMapper {

    //RequestDTO -> Entity
    Comando toEntity(ComandoRequestDTO comandoRequestDTO);

    //Entity -> ResponseDTO
    @Mapping(source = "agente.id", target = "agenteId")
    ComandoResponseDTO toResponseDTO(Comando comando);

    //Entity -> ResumoResponseDTO
    ComandoResumoResponseDTO toResumoResponseDTO(Comando comando);
}
