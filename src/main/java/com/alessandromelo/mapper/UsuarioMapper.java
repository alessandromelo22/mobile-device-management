package com.alessandromelo.mapper;

import com.alessandromelo.dto.departamento.DepartamentoResumoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.dto.usuario.*;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    //ResquestDTO -> Entity:
    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO){

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setMatricula(usuarioRequestDTO.getMatricula());
        usuario.setCargo(usuarioRequestDTO.getCargo());
        usuario.setAtivo(usuarioRequestDTO.getAtivo());

        return usuario;
    }

    //Entity -> ResponseDTO:
    public UsuarioResponseDTO toResponseDTO(Usuario usuario){

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNome(usuario.getNome());
        usuarioResponseDTO.setEmail(usuario.getEmail());
        usuarioResponseDTO.setMatricula(usuario.getMatricula());
        usuarioResponseDTO.setCargo(usuario.getCargo());
        usuarioResponseDTO.setAtivo(usuario.getAtivo());

        //DepartamentoResumo:
        if(usuario.getDepartamento() != null){

            DepartamentoResumoResponseDTO departamentoResumoResponseDTO = new DepartamentoResumoResponseDTO();
            departamentoResumoResponseDTO.setId(usuario.getDepartamento().getId());
            departamentoResumoResponseDTO.setNome(usuario.getDepartamento().getNome());

            usuarioResponseDTO.setDepartamentoResumoResponseDTO(departamentoResumoResponseDTO);
        }else {
            usuarioResponseDTO.setDepartamentoResumoResponseDTO(null);
        }

        return usuarioResponseDTO;
    }

    //Etity -> UsuarioDispositivoResponseDTO:
    public UsuarioDispositivoResponseDTO toUsuarioDispositivoResponseDTO(Usuario usuario, Dispositivo dispositivo){

        UsuarioDispositivoResponseDTO usuarioDispositivoResponseDTO = new UsuarioDispositivoResponseDTO();
        usuarioDispositivoResponseDTO.setUsuarioId(usuario.getId());
        usuarioDispositivoResponseDTO.setNome(usuario.getNome());
        usuarioDispositivoResponseDTO.setMatricula(usuario.getMatricula());

        //DispositivoResumo:
        DispositivoResumoResponseDTO dispositivoResumoResponseDTO = new DispositivoResumoResponseDTO();
        dispositivoResumoResponseDTO.setId(dispositivo.getId());
        dispositivoResumoResponseDTO.setModelo(dispositivo.getModelo());
        dispositivoResumoResponseDTO.setStatus(dispositivo.getStatus());

        usuarioDispositivoResponseDTO.setDispositivoResumoResponseDTO(dispositivoResumoResponseDTO);

        return usuarioDispositivoResponseDTO;
    }

    //Entity --> UsuarioDepartamentoResponseDTO:
    public UsuarioDepartamentoResponseDTO toUsuarioDepartamentoResponseDTO(Usuario usuario, Departamento departamento){

        UsuarioDepartamentoResponseDTO usuarioDepartamentoResponseDTO = new UsuarioDepartamentoResponseDTO();
        usuarioDepartamentoResponseDTO.setUsuarioId(usuario.getId());
        usuarioDepartamentoResponseDTO.setNome(usuario.getNome());
        usuarioDepartamentoResponseDTO.setMatricula(usuario.getMatricula());

        //DepartamentoResumo:
        DepartamentoResumoResponseDTO departamentoResumoResponseDTO = new DepartamentoResumoResponseDTO();
        departamentoResumoResponseDTO.setId(departamento.getId());
        departamentoResumoResponseDTO.setNome(departamento.getNome());

        usuarioDepartamentoResponseDTO.setDepartamentoResumoResponseDTO(departamentoResumoResponseDTO);

        return usuarioDepartamentoResponseDTO;
    }

    //Entity --> UsuarioResumoResponseDTO:
    public UsuarioResumoResponseDTO toResumoResponseDTO(Usuario usuario){

        UsuarioResumoResponseDTO usuarioResumoResponseDTO = new UsuarioResumoResponseDTO();

        usuarioResumoResponseDTO.setId(usuario.getId());
        usuarioResumoResponseDTO.setNome(usuario.getNome());
        usuarioResumoResponseDTO.setMatricula(usuario.getMatricula());

        return usuarioResumoResponseDTO;

    }

}
