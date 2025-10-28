package com.alessandromelo.service;

import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.exception.departamento.DepartamentoNaoEncontradoException;
import com.alessandromelo.exception.departamento.NomeJaCadastradoException;
import com.alessandromelo.exception.departamento.SiglaJaCadastradaException;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.mapper.DedepartamentoMapper;
import com.alessandromelo.mapper.DepartamentoMapper;
import com.alessandromelo.mapper.UsuarioMapper;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.mapper.UsusuarioMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private DepartamentoRepository departamentoRepository;
    private DedepartamentoMapper dedepartamentoMapper;

    private UsuarioRepository usuarioRepository;
    private UsusuarioMapper ususuarioMapper;


    public DepartamentoService(DepartamentoRepository departamentoRepository, DedepartamentoMapper dedepartamentoMapper, UsuarioRepository usuarioRepository, UsusuarioMapper ususuarioMapper) {
        this.departamentoRepository = departamentoRepository;
        this.dedepartamentoMapper = dedepartamentoMapper;
        this.usuarioRepository = usuarioRepository;

        this.ususuarioMapper = ususuarioMapper;
    }

    //Listar Departamentos:
    public List<DepartamentoResponseDTO> listarTodosDepartamentos(){

        List<Departamento> departamentos = this.departamentoRepository.findAll();

        return departamentos.stream().map(this.dedepartamentoMapper::toResponseDTO).toList();
    }

    //Buscar Departamento por Id:
    public DepartamentoResponseDTO buscarDepartamentoPorId(Long departamentoId){

        Departamento departamento = this.departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));

        return this.dedepartamentoMapper.toResponseDTO(departamento);
    }

    //Criar Departamento:
    public DepartamentoResponseDTO criarNovoDepartamento(DepartamentoRequestDTO novoDepartamentoDTO){

        Boolean nomeJaExiste = this.departamentoRepository.existsByNome(novoDepartamentoDTO.getNome());
        Boolean siglaJaExiste = this.departamentoRepository.existsBySigla(novoDepartamentoDTO.getSigla());

        if(nomeJaExiste){
            throw new NomeJaCadastradoException();
        } else if (siglaJaExiste) {
            throw new SiglaJaCadastradaException();
        }

        Departamento departamento = this.dedepartamentoMapper.toEntity(novoDepartamentoDTO);

        return this.dedepartamentoMapper.toResponseDTO(this.departamentoRepository.save(departamento));
    }

    //Atualizar Departamento: (CONSERTAR O PROBLEMA DE CAMPOS NULOS)
    public DepartamentoResponseDTO atualizarDepartamento(Long departamentoId, DepartamentoRequestDTO departamentoAtualizadoDTO){

        return this.departamentoRepository.findById(departamentoId)
                .map(departamento -> {

                    boolean nomeJaExiste = this.departamentoRepository.existsByNomeAndIdNot(departamentoAtualizadoDTO.getNome(), departamentoId);
                    boolean siglaJaExiste = this.departamentoRepository.existsBySiglaAndIdNot(departamentoAtualizadoDTO.getSigla(), departamentoId);

                    if (nomeJaExiste){
                        throw new NomeJaCadastradoException();
                    } else if (siglaJaExiste) {
                        throw new SiglaJaCadastradaException();
                    }

                    departamento.setNome(departamentoAtualizadoDTO.getNome());
                    departamento.setSigla(departamentoAtualizadoDTO.getSigla());

                    return this.dedepartamentoMapper.toResponseDTO(this.departamentoRepository.save(departamento));

                }).orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));
    }


//Remover Departamento:
    public void removerDepartamentoPorId(Long departamentoId){

        //verifica se o departamento existe
        Departamento departamento = this.departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));

        //verifica se à Usuarios associados a esse Departamento, caso true, a exclusão não pode acontecer
        boolean possuiUsuarios = this.usuarioRepository.existsByDepartamentoId(departamentoId);
        if(possuiUsuarios){
            throw new EntidadeEmUsoException(Departamento.class, departamentoId);
        }

        //caso passe por todas verificações, ai sim o Departamento é excluido.
        this.departamentoRepository.delete(departamento);
    }

//Listar Usuarios que pertencem ao Departamento:
    public List<UsuarioResumoResponseDTO> listarUsuariosDoDepartamento(Long departamentoId){

        List<Usuario> usuarios = this.departamentoRepository.findById(departamentoId).map(Departamento::getUsuarios)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));

        return usuarios.stream().map(this.ususuarioMapper::toResumoResponseDTO).toList();
    }

}
