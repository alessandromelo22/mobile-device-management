package com.alessandromelo.controller;

import com.alessandromelo.dto.metricasdispositivo.MetricasDispositivoResponseDTO;
import com.alessandromelo.service.MetricasDispositivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Métricas do Dispositivo", description = "Endpoints voltados para o gerenciamento das Métricas dos Dispositivos ")
@RestController
@RequestMapping("/metricas-dispositivos")
public class MetricasDispositivoController {


    private MetricasDispositivoService metricasDispositivoService;


    public MetricasDispositivoController(MetricasDispositivoService metricasDispositivoService) {
        this.metricasDispositivoService = metricasDispositivoService;
    }


    @Operation(
            summary = "Buscar todas as métricas enviadas por um Agente",
            description = "Retorna uma lista com todas as métricas que estão associadas à um determinado Agente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Métricas retornadas com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Id enviado inválido!"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado!")
    })
    @GetMapping("/agentes/{agenteId}")
    public ResponseEntity<List<MetricasDispositivoResponseDTO>> buscarMetricasVinculadasAoAgente (
            @Parameter(description = "Id do Agente a ser buscado", example = "3")
            @PathVariable Long agenteId){

        return ResponseEntity.ok(this.metricasDispositivoService.buscarMetricasVinculadasAoAgente(agenteId));
    }



    @DeleteMapping("/{metricasDispositivoId}")
    public ResponseEntity<Void> deletarMetricasDispositivoPorId (@PathVariable Long metricasDispositivoId){

        this.metricasDispositivoService.deletarMetricasDispositivoPorId(metricasDispositivoId);
        return ResponseEntity.noContent().build(); //204
    }
}
