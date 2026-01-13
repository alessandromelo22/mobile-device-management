package com.alessandromelo.service;


import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarlogs.EnviarLogsResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasRequestDTO;
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
 * <p><b>AgenteOperacoesService</b></p>
 *
 * <p>Endpoints <b>chamados pelo próprio Agente</b>
 *  (representa as operações que o Agente consegue fazer)</p>
 *
 *<p>DTOs de Request e Response nomeados com o nome da operação para não misturar com DTOs de Persistência.</p>
 *
 * {@link AgenteService} -> para ver as operacões de persistência e as regras de negócio.
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
     * Chamado pelo próprio Agente, atualiza apenas alguns campos, usado como um <b>"heartbeat"</b>
     * diferente do metodo <b>atualizarAgente()</b> de {@link AgenteService}  que possibilita atualizar todos os campos.
     *
     * @param agenteId id do Agente
     * @param requestDTO versao e status do Agente
     * @return AtualizarStatusResponseDTO com id, versao, status e dataUltimaAtividade
     * @throws AgenteNaoEncontradoException se o Agente não existir no banco
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
     * O Agente busca no banco se há <b>comandos com o status como PENDENTE</b>.
     *
     * @param agenteId Id do Agente
     * @return BuscarComandosPendentesResponseDTO com id do Agente junto com a lista de comandos PENDENTES, <b>ou</b> uma lista vazia se caso não houver nenhum comando
     * @throws AgenteNaoEncontradoException se o Agente não existir no banco
     */

    public BuscarComandosPendentesResponseDTO buscarComandosPendentes(Long agenteId){

        Agente agente = this.agenteRepository.findById(agenteId)
                .orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

        List<Comando> comandos = this.comandoRepository.findByAgenteIdAndStatusOrderByDataCriacaoAsc(agenteId, ComandoStatus.PENDENTE);

        return this.buscarComandosPendentesMapper.toResponseDTO(agenteId, comandos);
    }

    /**
     * O Agente chama esse endpoint enviando as Metricas do Dispositivo para ser armazenado no banco de dados
     *
     * @param agenteId Id do Agente
     * @param agenteId Id do Agente
     */

    public void enviarMetricas(Long agenteId, EnviarMetricasRequestDTO requestDTO){


    }



//    public EnviarLogsResponseDTO enviarLogs(){
//        // logica para receber e armazenar os logs enviados pelo agente
//        return null;
//    }
}
