package com.alessandromelo.controller;

import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.service.AgenteOperacoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Agente Operações", description = "Endpoints que serão utilizado pelo próprio Agente.")
@RestController
@RequestMapping("/agentes/{agenteId}/operacoes")
public class AgenteOperacoesController {

    private AgenteOperacoesService agenteOperacoesService;


    public AgenteOperacoesController(AgenteOperacoesService agenteOperacoesService) {
        this.agenteOperacoesService = agenteOperacoesService;
    }



    @Operation(
            summary = "Atualizar status do Agente",
            description = "Atualiza o status de um Usuario existente com base no ID informado. Diferente do endpoint atualizarAgente() que atualiza o Agente como um todo, esse endpoint é usado apenas como um 'heartbeat.'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    @PutMapping("/status")
    public ResponseEntity<AtualizarStatusResponseDTO> atualizarStatus(
            @Parameter(description = "ID do Agente a ser atualizado", example = "2")
            @PathVariable Long agenteId,
            @RequestBody @Valid AtualizarStatusRequestDTO requestDTO){

        return ResponseEntity.ok(this.agenteOperacoesService.atualizarStatus(agenteId,requestDTO));
    }


    @Operation(
            summary = "Buscar Comandos pendentes",
            description = "Retorna uma lista contendo os Comandos com o status PENDENTE de um Agente específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comandos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado")

    })
    @GetMapping("/comandos-pendentes")
    public ResponseEntity<BuscarComandosPendentesResponseDTO> buscarComandosPendentes(
            @Parameter(description = "ID do Agente a ser consultado", example = "2")
            @PathVariable Long agenteId){

        return ResponseEntity.ok(this.agenteOperacoesService.buscarComandosPendentes(agenteId));
    }



    @Operation(
            summary = "Enviar métricas do Dispositivo",
            description = "Envia as métricas do Dispositivo para serem armazenadas no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Métricas enviadas para o banco com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos!"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado!")
    })
    @PostMapping("/enviar-metricas")
    public ResponseEntity<EnviarMetricasResponseDTO> enviarMetricas (
            @Parameter(description = "Id do Agente que está enviando as Métricas", example = "1")
            @PathVariable Long agenteId,
            @RequestBody @Valid EnviarMetricasRequestDTO requestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.agenteOperacoesService.enviarMetricas(agenteId, requestDTO));
    }





}
