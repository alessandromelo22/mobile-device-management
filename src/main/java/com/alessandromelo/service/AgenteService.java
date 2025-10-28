package com.alessandromelo.service;

import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.mapper.AgenteMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.DispositivoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Operações CRUD de Agente, operações essas que serão chamadas
 * por algum adminstrador e não pelo Agente em sí.
*/
@Service
public class AgenteService {

    private AgenteRepository agenteRepository;
    private AgenteMapper agenteMapper;

    private DispositivoRepository dispositivoRepository;


    public AgenteService(AgenteRepository agenteRepository, AgenteMapper agenteMapper, DispositivoRepository dispositivoRepository) {
        this.agenteRepository = agenteRepository;
        this.agenteMapper = agenteMapper;
        this.dispositivoRepository = dispositivoRepository;
    }

    //Buscar todos
    public List<AgenteResponseDTO> listarTodosAgentes() {

        List<Agente> agentes = this.agenteRepository.findAll();
        return agentes.stream().map(agenteMapper::toResponseDTO).toList();
    }

//Buscar por Id
    public AgenteResponseDTO buscarAgentePorId(Long agenteId){

        Agente agente = this.agenteRepository.findById(agenteId)
                .orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

        return this.agenteMapper.toResponseDTO(agente);
    }


//Buscar por status

    public List<AgenteResponseDTO> buscarAgentesPorStatus(AgenteStatus status){

        List<Agente> agentes = this.agenteRepository.findByStatus(status);
        return agentes.stream().map(this.agenteMapper::toResponseDTO).toList();
    }


//Cadastrar novo Agente
    public AgenteResponseDTO cadastrarNovoAgente(AgenteRequestDTO novoAgenteDTO) {

        Agente agente = this.agenteMapper.toEntity(novoAgenteDTO);

        if(novoAgenteDTO.getDispositivoId() != null){

            Dispositivo dispositivo = this.dispositivoRepository.findById(novoAgenteDTO.getDispositivoId())
                    .orElseThrow(() -> new DispositivoNaoEncontradoException(novoAgenteDTO.getDispositivoId()));

            agente.setDispositivo(dispositivo);
        }
        return this.agenteMapper.toResponseDTO(this.agenteRepository.save(agente));
    }

//Atualizar Agente CRUD:
    public AgenteResponseDTO atualizarAgente(Long agenteId, AgenteRequestDTO agenteAtualizadoDTO){

        return this.agenteRepository.findById(agenteId)
                .map(agente -> {

                    agente.setVersao(agenteAtualizadoDTO.getVersao());
                    agente.setLog(agenteAtualizadoDTO.getLog());
                    agente.setDataUltimaAtividade(LocalDateTime.now()); //setta a data e hora atual da chamada

                    if(agenteAtualizadoDTO.getDispositivoId() != null){

                        Dispositivo dispositivo = this.dispositivoRepository.findById(agenteAtualizadoDTO.getDispositivoId())
                                .orElseThrow(() -> new DispositivoNaoEncontradoException(agenteAtualizadoDTO.getDispositivoId()));

                        agente.setDispositivo(dispositivo);
                    }
                    return this.agenteMapper.toResponseDTO(this.agenteRepository.save(agente));

                }).orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));
    }


//Desativar Agente
    public AgenteResumoResponseDTO desativarAgente(Long agenteId){

        return this.agenteRepository.findById(agenteId)
                .map(agente -> {

                    agente.setStatus(AgenteStatus.INATIVO);
                    agente.setDataUltimaAtividade(LocalDateTime.now()); //setta a data e hora atual da chamada desse endpoint

                    return this.agenteMapper.toResumoResponseDTO(this.agenteRepository.save(agente));

                }).orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

    }


//Ativar Agente:
    public AgenteResumoResponseDTO ativarAgente(Long agenteId){

        return this.agenteRepository.findById(agenteId)
                .map(agente -> {

                    agente.setStatus(AgenteStatus.ATIVO);
                    agente.setDataUltimaAtividade(LocalDateTime.now()); //setta a data e hora atual da chamada desse endpoint

                    return this.agenteMapper.toResumoResponseDTO(this.agenteRepository.save(agente));

                }).orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));
    }



//Remover Agente: TEMPORARIO
    public void removerAgentePorId(Long agenteId){
        this.agenteRepository.deleteById(agenteId);
    }

}
