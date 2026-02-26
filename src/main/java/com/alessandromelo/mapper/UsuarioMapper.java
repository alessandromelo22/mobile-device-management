package com.alessandromelo.mapper;


import com.alessandromelo.dto.usuario.*;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DepartamentoMapper.class})
public interface UsuarioMapper {

    //RequestDTO -> Entity
    Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

    //Entity -> ResponseDTO
    @Mapping(source = "departamento", target = "departamentoResumoResponseDTO")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    //Entity -> UsuarioDepartamentoResponseDTO
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nome", target = "nome")
    @Mapping(source = "usuario.matricula", target = "matricula")
    @Mapping(source = "departamento", target = "departamentoResumoResponseDTO")
    UsuarioDepartamentoResponseDTO toUsuarioDepartamentoResponseDTO(Usuario usuario, Departamento departamento);

    //Entity -> UsuarioResumoResponse
    UsuarioResumoResponseDTO toResumoResponseDTO(Usuario usuario);

    //UsuarioImportDTO -> Entity
    @Mapping(target = "departamento", ignore = true)
    Usuario toEntity(UsuarioImportDTO usuarioImportDTO);
}
