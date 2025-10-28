package com.alessandromelo.mapper;

import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.entity.Dispositivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface DispositivoMapper {

    //RequestDTO -> Entity:
    Dispositivo toEntity(DispositivoRequestDTO dispositivoRequestDTO);

    //Entity -> DispositivoresponseDTO
    @Mapping(source = "usuario", target = "usuarioResumoResponseDTO")
    DispositivoResponseDTO toResponseDTO(Dispositivo dispositivo);

    //Entity -> resumoResponseDTO:
    DispositivoResumoResponseDTO toResumoResponseDTO(Dispositivo dispositivo);

    //Aparentemente está pronto
}
