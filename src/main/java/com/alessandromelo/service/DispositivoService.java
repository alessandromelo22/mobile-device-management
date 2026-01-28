package com.alessandromelo.service;

import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.exception.dispositivo.NumeroDeSerieJaCadastradoException;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.exception.usuario.UsuarioNaoEncontradoException;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.mapper.DispositivoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.DispositivoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoService {

    private DispositivoRepository dispositivoRepository;
    private DispositivoMapper dispositivoMapper;

    private UsuarioRepository usuarioRepository;

    private AgenteRepository agenteRepository;


    public DispositivoService(DispositivoRepository dispositivoRepository, DispositivoMapper dispositivoMapper, UsuarioRepository usuarioRepository, AgenteRepository agenteRepository) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.usuarioRepository = usuarioRepository;
        this.agenteRepository = agenteRepository;
    }

    //Listar todos os dispositivos:
    public List<DispositivoResponseDTO> listarTodosDispositivos(){

        List<Dispositivo> dispositivos = this.dispositivoRepository.findAll();
        return dispositivos.stream().map(this.dispositivoMapper::toResponseDTO).toList();
    }


//Buscar Dispositivo por Id:
    public DispositivoResponseDTO buscarDispositivoPorId(Long dispositivoId){

        Dispositivo dispositivo = this.dispositivoRepository.findById(dispositivoId)
                .orElseThrow(() -> new DispositivoNaoEncontradoException(dispositivoId));

        return this.dispositivoMapper.toResponseDTO(dispositivo);
    }

//Buscar Dispositivos por Status:
    public List<DispositivoResponseDTO> buscarDispositivosPorStatus(DispositivoStatus status){

        List<Dispositivo> dispositivos = this.dispositivoRepository.findByStatus(status);
        return dispositivos.stream().map(this.dispositivoMapper::toResponseDTO).toList();
    }



//Cadastrar Dispositivo:
    public DispositivoResponseDTO cadastrarNovoDispositivo(DispositivoRequestDTO novoDispositivoDTO){

        boolean numeroSerieExistente = this.dispositivoRepository.existsByNumeroSerie(novoDispositivoDTO.getNumeroSerie());

        if(numeroSerieExistente){
            throw new NumeroDeSerieJaCadastradoException();
        }

        Dispositivo dispositivo = this.dispositivoMapper.toEntity(novoDispositivoDTO);
        dispositivo.setStatus(DispositivoStatus.ATIVO);

        if(novoDispositivoDTO.getUsuarioId() != null){

            Usuario usuario = this.usuarioRepository.findById(novoDispositivoDTO.getUsuarioId())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(novoDispositivoDTO.getUsuarioId()));

            dispositivo.setUsuario(usuario);
        }

        return this.dispositivoMapper.toResponseDTO(this.dispositivoRepository.save(dispositivo));
    }

//Atualizar Dispositivo: (CONSERTAR O PROBLEMA DE CAMPOS NULOS)
    public DispositivoResponseDTO atualizarDispositivo(Long dispositivoId, DispositivoRequestDTO dispositivoAtualizadoDTO){

       return this.dispositivoRepository.findById(dispositivoId)
               .map(dispositivo -> {

                   boolean numeroSerieExistente = this.dispositivoRepository.existsByNumeroSerieAndIdNot(dispositivoAtualizadoDTO.getNumeroSerie(), dispositivoId);

                   if (numeroSerieExistente){
                       throw new NumeroDeSerieJaCadastradoException();
                   }

                   dispositivo.setModelo(dispositivoAtualizadoDTO.getModelo());
                   dispositivo.setMarca(dispositivoAtualizadoDTO.getMarca());
                   dispositivo.setNumeroSerie(dispositivoAtualizadoDTO.getNumeroSerie());
                   dispositivo.setSistemaOperacional(dispositivoAtualizadoDTO.getSistemaOperacional());
                   dispositivo.setVersaoSO(dispositivoAtualizadoDTO.getVersaoSO());
                   dispositivo.setDataUltimaAtualizacao(dispositivoAtualizadoDTO.getDataUltimaAtualizacao());
                   dispositivo.setDataAquisicao(dispositivoAtualizadoDTO.getDataAquisicao());
                   dispositivo.setObservacoes(dispositivoAtualizadoDTO.getObservacoes());
                   dispositivo.setStatus(dispositivoAtualizadoDTO.getStatus());

                   if(dispositivoAtualizadoDTO.getUsuarioId() != null){

                       Usuario usuario = this.usuarioRepository.findById(dispositivoAtualizadoDTO.getUsuarioId())
                               .orElseThrow(() -> new UsuarioNaoEncontradoException(dispositivoAtualizadoDTO.getUsuarioId()));

                       dispositivo.setUsuario(usuario);
                   }

                   return this.dispositivoMapper.toResponseDTO(this.dispositivoRepository.save(dispositivo));

               }).orElseThrow(() -> new DispositivoNaoEncontradoException(dispositivoId));
    }

//Remover Dispositivo:
    public void removerDispositivoPorId(Long dispositivoId){

        Dispositivo dispositivo = this.dispositivoRepository.findById(dispositivoId)
                .orElseThrow(() -> new DispositivoNaoEncontradoException(dispositivoId));


        boolean possuiAgente = this.agenteRepository.existsByDispositivoId(dispositivoId);
        if(possuiAgente){
            throw new EntidadeEmUsoException(Dispositivo.class, dispositivoId);
        }

        this.dispositivoRepository.delete(dispositivo);
    }


}
