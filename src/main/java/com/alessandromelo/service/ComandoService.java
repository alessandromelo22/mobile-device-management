package com.alessandromelo.service;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.exception.comando.ComandoNaoEncontradoException;
import com.alessandromelo.mapper.CocomandoMapper;
import com.alessandromelo.mapper.ComandoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.ComandoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComandoService {

    private ComandoRepository comandoRepository;
    private CocomandoMapper cocomandoMapper;

    private AgenteRepository agenteRepository;


    public ComandoService(ComandoRepository comandoRepository, CocomandoMapper cocomandoMapper, AgenteRepository agenteRepository) {
        this.comandoRepository = comandoRepository;
        this.cocomandoMapper = cocomandoMapper;
        this.agenteRepository = agenteRepository;
    }

    //GET
    public List<ComandoResponseDTO> buscarTodosComandos(){

        List<Comando> comandos = this.comandoRepository.findAll();
        return comandos.stream().map(this.cocomandoMapper::toResponseDTO).toList();
    }

//GET
    public List<ComandoResponseDTO> buscarComandosPorStatus(ComandoStatus status){

        List<Comando> comandos = this.comandoRepository.findByStatus(status);
        return comandos.stream().map(this.cocomandoMapper::toResponseDTO).toList();
    }

//GET
    public ComandoResponseDTO buscarComandoPorId(Long comandoId){

        Comando comando = this.comandoRepository.findById(comandoId)
                .orElseThrow(() -> new ComandoNaoEncontradoException(comandoId));

        return this.cocomandoMapper.toResponseDTO(comando);
    }

//POST
    public ComandoResponseDTO criarComando(ComandoRequestDTO novoComandoDTO){

        Comando comando = this.cocomandoMapper.toEntity(novoComandoDTO);

        Agente agente = this.agenteRepository.findById(novoComandoDTO.getAgenteId())
                .orElseThrow(() -> new AgenteNaoEncontradoException(novoComandoDTO.getAgenteId()));

        comando.setAgente(agente);

        return this.cocomandoMapper.toResponseDTO(this.comandoRepository.save(comando));

    }


    //TEMPORÁRIO
    public void deletarComando(Long comandoId) {

        Comando comando = this.comandoRepository.findById(comandoId)
                .orElseThrow(() -> new ComandoNaoEncontradoException(comandoId));

        this.comandoRepository.delete(comando);
    }
}
