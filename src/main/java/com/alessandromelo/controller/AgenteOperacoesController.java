package com.alessandromelo.controller;

import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.service.AgenteOperacoesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/agentes/{agenteId}/operacoes")
public class AgenteOperacoesController {

    private AgenteOperacoesService agenteOperacoesService;

    public AgenteOperacoesController(AgenteOperacoesService agenteOperacoesService) {
        this.agenteOperacoesService = agenteOperacoesService;
    }

    /**
     * Usado pelo próprio Agente como um heartbeat
     * @param agenteId
     * @param requestDTO
     * @return
     */
    @PutMapping("/status")
    public ResponseEntity<AtualizarStatusResponseDTO> atualizarStatus(@PathVariable Long agenteId,
                                                                      @RequestBody @Valid AtualizarStatusRequestDTO requestDTO){

        return ResponseEntity.ok(this.agenteOperacoesService.atualizarStatus(agenteId,requestDTO));
    }

    /**
     * O Agente chama esse endpoint para buscar quais comandos estão PENDENTES
     * @param agenteId
     * @return
     */
// O agente consulta quais ordens precisa executar (ex: bloquear tela).
    @GetMapping("/comandos-pendentes")
    public ResponseEntity<BuscarComandosPendentesResponseDTO> buscarComandosPendentes(@PathVariable Long agenteId){

        return ResponseEntity.ok(this.agenteOperacoesService.buscarComandosPendentes(agenteId));
    }

    // POST
// O agente envia mensagens ou erros para o servidor.
    public ResponseEntity<Agente> enviarLogsDeExecução(){

        return null;
    }





}
