package com.alessandromelo.service;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.exception.comando.ComandoNaoEncontradoException;
import com.alessandromelo.mapper.ComandoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.ComandoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComandoService {

    private ComandoRepository comandoRepository;
    private ComandoMapper comandoMapper;

    private AgenteRepository agenteRepository;


    public ComandoService(ComandoRepository comandoRepository, ComandoMapper comandoMapper, AgenteRepository agenteRepository) {
        this.comandoRepository = comandoRepository;
        this.comandoMapper = comandoMapper;
        this.agenteRepository = agenteRepository;
    }

    //GET
    public List<ComandoResponseDTO> buscarTodosComandosVinculadosAoAgente(Long agenteId){

        Agente agente = this.agenteRepository.findById(agenteId)
                .orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

        List<Comando> comandos = this.comandoRepository.findByAgenteId(agenteId);

        return comandos.stream().map(this.comandoMapper::toResponseDTO).toList();
    }

//GET
    public List<ComandoResponseDTO> buscarComandosPorStatus(ComandoStatus status){

        List<Comando> comandos = this.comandoRepository.findByStatus(status);
        return comandos.stream().map(this.comandoMapper::toResponseDTO).toList();
    }

//GET
    public ComandoResponseDTO buscarComandoPorId(Long comandoId){

        Comando comando = this.comandoRepository.findById(comandoId)
                .orElseThrow(() -> new ComandoNaoEncontradoException(comandoId));

        return this.comandoMapper.toResponseDTO(comando);
    }

//POST
    public ComandoResponseDTO criarComando(ComandoRequestDTO novoComandoDTO){

        Comando comando = this.comandoMapper.toEntity(novoComandoDTO);

        Agente agente = this.agenteRepository.findById(novoComandoDTO.getAgenteId())
                .orElseThrow(() -> new AgenteNaoEncontradoException(novoComandoDTO.getAgenteId()));

        comando.setAgente(agente);
        comando.setStatus(ComandoStatus.PENDENTE);
        comando.setDataCriacao(LocalDateTime.now());

        return this.comandoMapper.toResponseDTO(this.comandoRepository.save(comando));

    }


    //TEMPORÁRIO
    public void deletarComando(Long comandoId) {

        Comando comando = this.comandoRepository.findById(comandoId)
                .orElseThrow(() -> new ComandoNaoEncontradoException(comandoId));

        this.comandoRepository.delete(comando);
    }
}
