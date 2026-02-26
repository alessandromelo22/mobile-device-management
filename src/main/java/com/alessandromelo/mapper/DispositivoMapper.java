package com.alessandromelo.mapper;

import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoUsuarioResponseDTO;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface DispositivoMapper {

    //RequestDTO -> Entity:
    Dispositivo toEntity(DispositivoRequestDTO dispositivoRequestDTO);

    //Entity -> DispositivoResponseDTO
    @Mapping(source = "usuario", target = "usuarioResumoResponseDTO")
    DispositivoResponseDTO toResponseDTO(Dispositivo dispositivo);

    //Entity -> DispositivoResumoResponseDTO:
    DispositivoResumoResponseDTO toResumoResponseDTO(Dispositivo dispositivo);

    //Entity -> DispositivoUsuarioResponseDTO:
    @Mapping(source = "dispositivo.id", target = "dispositivoId")
    @Mapping(source = "dispositivo.modelo", target = "modelo")
    @Mapping(source = "dispositivo.status", target = "status")
    @Mapping(source = "usuario", target = "usuarioResumoResponseDTO")
    DispositivoUsuarioResponseDTO toDispositivoUsuarioResponseDTO (Dispositivo dispositivo, Usuario usuario);
}
