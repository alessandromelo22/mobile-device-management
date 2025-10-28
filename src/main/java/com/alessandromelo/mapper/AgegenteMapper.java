package com.alessandromelo.mapper;

import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DisdispositivoMapper.class})
public interface AgegenteMapper {

    //RequestDTO -> Entity
    Agente toEntity(AgenteRequestDTO agenteRequestDTO);

    //Entity -> ResponseDTO
    @Mapping(source = "dispositivo", target = "dispositivoResumoResponseDTO")
    AgenteResponseDTO toResponseDTO(Agente agente);

    //Entity -> ResumoResponseDTO
    AgenteResumoResponseDTO toResumoResponseDTO(Agente agente);

}
