package com.alessandromelo.service;

import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDispositivoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioRequestDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.exception.usuario.EmailJaCadastradoException;
import com.alessandromelo.exception.usuario.MatriculaJaCadastradaException;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.mapper.DispositivoMapper;
import com.alessandromelo.mapper.UsuarioMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import com.alessandromelo.repository.DispositivoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import com.alessandromelo.exception.departamento.DepartamentoNaoEncontradoException;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.exception.usuario.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;

    private DispositivoRepository dispositivoRepository;
    private DispositivoMapper dispositivoMapper;

    private DepartamentoRepository departamentoRepository;


    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, DispositivoRepository dispositivoRepository, DispositivoMapper dispositivoMapper, DepartamentoRepository departamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.departamentoRepository = departamentoRepository;
    }

    //Listar todos os usuarios:
    public List<UsuarioResponseDTO> listarUsuarios(){

        List<Usuario> usuarios = this.usuarioRepository.findAll();
        return usuarios.stream().map(usuarioMapper::toResponseDTO).toList();
    }

//Buscar Usuario por id: (Modificado, agora lança uma exception)
    public UsuarioResponseDTO buscarUsuarioPorId(Long usuarioId){

        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        return this.usuarioMapper.toResponseDTO(usuario);
    }




//Cadastrar novo Usuario:
    public UsuarioResponseDTO cadastrarNovoUsuario(UsuarioRequestDTO novoUsuarioDTO){
        //1
        boolean emailJaExiste = this.usuarioRepository.existsByEmail(novoUsuarioDTO.getEmail());
        boolean matriculaJaExiste = this.usuarioRepository.existsByMatricula(novoUsuarioDTO.getMatricula());

        if (emailJaExiste){
            throw new EmailJaCadastradoException();
        } else if(matriculaJaExiste){
            throw new MatriculaJaCadastradaException();
        }
        //2
        Usuario usuario = this.usuarioMapper.toEntity(novoUsuarioDTO);
        //3
        if (novoUsuarioDTO.getDepartamentoId() != null){

            Departamento departamento = this.departamentoRepository.findById(novoUsuarioDTO.getDepartamentoId())
                    .orElseThrow(() -> new DepartamentoNaoEncontradoException(novoUsuarioDTO.getDepartamentoId()));
            //4
            usuario.setDepartamento(departamento);
        }

        return this.usuarioMapper.toResponseDTO(this.usuarioRepository.save(usuario));
    }


//Atualizar Usuario: (Está correto porem futuramente deve ser corrigido o problema dos possiveis campos nulos)
    public UsuarioResponseDTO atualizarUsuario(Long usuarioId, UsuarioRequestDTO usuarioAtualizadoDTO){

        return this.usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    //1
                    boolean emailJaExiste = this.usuarioRepository.existsByEmailAndIdNot(usuarioAtualizadoDTO.getEmail(),usuarioId);
                    boolean matriculaJaExiste = this.usuarioRepository.existsByMatriculaAndIdNot(usuarioAtualizadoDTO.getMatricula(),usuarioId);

                    if(emailJaExiste){
                        throw new EmailJaCadastradoException();
                    }else if(matriculaJaExiste){
                        throw new MatriculaJaCadastradaException();
                    }

                    usuario.setNome(usuarioAtualizadoDTO.getNome());
                    usuario.setCargo(usuarioAtualizadoDTO.getCargo());
                    usuario.setMatricula(usuarioAtualizadoDTO.getMatricula());
                    usuario.setEmail(usuarioAtualizadoDTO.getEmail());
                    usuario.setAtivo(usuarioAtualizadoDTO.getAtivo());

                    if(usuarioAtualizadoDTO.getDepartamentoId() != null){

                        Departamento departamento = this.departamentoRepository.findById(usuarioAtualizadoDTO.getDepartamentoId())
                                .orElseThrow(() -> new DepartamentoNaoEncontradoException(usuarioAtualizadoDTO.getDepartamentoId()));

                        usuario.setDepartamento(departamento);
                    }

                    return this.usuarioMapper.toResponseDTO(this.usuarioRepository.save(usuario));

                } ).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

//Remover Usuario:
    public void removerUsuarioPorId(Long usuarioId){

        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        boolean possuiDispositivos = this.dispositivoRepository.existsByUsuarioId(usuarioId);
        if(possuiDispositivos){
            throw new EntidadeEmUsoException(Usuario.class, usuarioId);
        }

        this.usuarioRepository.delete(usuario);
    }

//Listar Dispositivos cadastrados em um determinado Usuario   (CERTO)
    public List<DispositivoResumoResponseDTO> listarDispositivosVinculadosAoUsuario(Long usuarioId){

        List<Dispositivo> dispositivos = this.usuarioRepository.findById(usuarioId).map(Usuario::getDispositivos)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        return dispositivos.stream().map(this.dispositivoMapper::toResumoResponseDTO).toList();
    }

//Setar Dispositivo a um Usuario:
    public UsuarioDispositivoResponseDTO vincularDispositivoAoUsuario(Long usuarioId, Long dispositivoId){

        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new UsuarioNaoEncontradoException(usuarioId));

        Dispositivo dispositivo = this.dispositivoRepository.findById(dispositivoId)
                .orElseThrow(() -> new DispositivoNaoEncontradoException(dispositivoId));

        dispositivo.setUsuario(usuario);

        this.dispositivoRepository.save(dispositivo);

        return this.usuarioMapper.toUsuarioDispositivoResponseDTO(usuario, dispositivo);
    }


//Setar Departamento a um Usuario:
    public UsuarioDepartamentoResponseDTO vincularUsuarioAoDepartamento(Long usuarioId, Long departamentoId){

        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Departamento departamento = this.departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));

        usuario.setDepartamento(departamento);

        this.usuarioRepository.save(usuario);

        return this.usuarioMapper.toUsuarioDepartamentoResponseDTO(usuario, departamento);
    }


}
