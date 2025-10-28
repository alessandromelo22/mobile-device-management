package com.alessandromelo.mapper;


import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDispositivoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioRequestDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DedepartamentoMapper.class})
public interface UsusuarioMapper {

    //RequestDTO -> Entity
    Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

    //Entity -> ResponseDTO
    @Mapping(source = "departamento", target = "departamentoResumoResponseDTO")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    //Entity -> UsuarioDispositivoResponseDTO
    @Mapping(source ="usuario.id", target = "usuarioId")
    @Mapping(source ="usuario.nome", target = "nome")
    @Mapping(source ="usuario.matricula", target = "matricula")
    @Mapping(source ="dispositivo", target = "dispositivoResumoResponseDTO") //dispositivo aqui faz referência ao objeto Dispositivo que veio como argumento
    UsuarioDispositivoResponseDTO toUsuarioDispositivoResponseDTO(Usuario usuario, Dispositivo dispositivo);

    //Entity -> UsuarioDepartamentoResponseDTO
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nome", target = "nome")
    @Mapping(source = "usuario.matricula", target = "matricula")
    @Mapping(source = "departamento", target = "departamentoResumoResponseDTO")
    UsuarioDepartamentoResponseDTO toUsuarioDepartamentoResponseDTO(Usuario usuario, Departamento departamento);

    //Entity -> UsuarioResumoResponse
    UsuarioResumoResponseDTO toResumoResponseDTO(Usuario usuario);
}
