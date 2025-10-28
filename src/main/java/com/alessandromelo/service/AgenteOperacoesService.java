package com.alessandromelo.service;


import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarlogs.EnviarLogsResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.mapper.agenteoperacoes.AtualizarStatusMapper;
import com.alessandromelo.mapper.agenteoperacoes.BuscarComandosPendentesMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.ComandoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Endpoints chamados pelo próprio Agente
 * (representa as operações que o Agente consegue fazer)
 */


@Service
public class AgenteOperacoesService {


    private AgenteRepository agenteRepository;
    private AtualizarStatusMapper atualizarStatusMapper;

    private ComandoRepository comandoRepository;
    private BuscarComandosPendentesMapper buscarComandosPendentesMapper;

    public AgenteOperacoesService(AgenteRepository agenteRepository, AtualizarStatusMapper atualizarStatusMapper, ComandoRepository comandoRepository, BuscarComandosPendentesMapper buscarComandosPendentesMapper) {
        this.agenteRepository = agenteRepository;
        this.atualizarStatusMapper = atualizarStatusMapper;
        this.comandoRepository = comandoRepository;
        this.buscarComandosPendentesMapper = buscarComandosPendentesMapper;
    }

    //PUT

    /**
     * Chamado pelo próprio Agente, atualiza apenas alguns campos,
     * diferente do endpoint AtualizarAgente() que possibilita atualizar todos os campos
     *
     * @param agenteId  id do Agente
     * @param requestDTO versao e status do Agente
     * @return AtualizarStatusResponseDTO com id, versao, status e dataUltimaAtividade
     */

    public AtualizarStatusResponseDTO atualizarStatus(Long agenteId, AtualizarStatusRequestDTO requestDTO){

        return this.agenteRepository.findById(agenteId)
                .map(agente -> {

                    agente.setVersao(requestDTO.getVersao());
                    agente.setDataUltimaAtividade(LocalDateTime.now()); //setta a data e hora atual da chamada desse endpoint

                    return this.atualizarStatusMapper.toResponseDTO(this.agenteRepository.save(agente));

                }).orElseThrow(()-> new AgenteNaoEncontradoException(agenteId));
    }



    /**
     * O agente busca no banco se há comandos PENDENTES
     *
     * @param agenteId Id do Agente
     * @return BuscarComandosPendentesResponseDTO com id do Agente junto com a lista de comandos PENDENTES, ou uma lista vazia se caso não houver nenhum comando
     * @throws AgenteNaoEncontradoException se o Agente não existir no banco
     */

    public BuscarComandosPendentesResponseDTO buscarComandosPendentes(Long agenteId){

        Agente agente = this.agenteRepository.findById(agenteId)
                .orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

        List<Comando> comandos = this.comandoRepository.findByAgenteIdAndStatusOrderByDataCriacaoAsc(agenteId, ComandoStatus.PENDENTE);

        return this.buscarComandosPendentesMapper.toResponseDTO(agenteId, comandos);
    }





    public void atualizarStatusComando(Long comandoId){
        //logica para atualizar o status do comando (EXECUTADO ou FALHA) com base no comandoId e requestDTO
    }

    public EnviarLogsResponseDTO enviarLogs(){
        // logica para receber e armazenar os logs enviados pelo agente
        return null;
    }
}
