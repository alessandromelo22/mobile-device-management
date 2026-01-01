package com.alessandromelo.controller;


import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.service.AgenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Operações CRUD de Agente, operações essas que serão chamadas
 * por algum adminstrador e não pelo Agente em sí.
 */
@Tag(name = "Agente", description = "Operações voltadas para o gerenciamento de Agentes")
@RestController
@RequestMapping("/agentes")
public class AgenteController {

    private AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }



//Buscar todos:
    @Operation(
            summary = "Listar todos os Agentes",
            description = "Retorna uma lista com todos os Agentes salvos no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agentes retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<AgenteResponseDTO>> buscarTodosAgentes(){
       return ResponseEntity.ok(this.agenteService.listarTodosAgentes());
    }


//Buscar por Id
    @Operation(
            summary = "Buscar Agente pelo ID",
            description = "Retorna um Agente específico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agente retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado")
            //Colocar o Response caso o argumento seja inválido(MethodArgumentTypeMismatchException)

    })
    @GetMapping("/{agenteId}")
    public ResponseEntity<AgenteResponseDTO> buscarAgentePorId(
            @Parameter(description = "ID do Agente a ser buscado", example = "2")
            @PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.buscarAgentePorId(agenteId));
    }

//Buscar por status:
    @Operation(
            summary = "Buscar Agente pelo status",
            description = "Retorna um Agente específico com base no status informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agente retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status informado inválido")
    })
    @GetMapping("/status")
    public ResponseEntity<List<AgenteResponseDTO>> buscarAgentesPorStatus(
            @Parameter(description = "Status dos Agentes a sererm buscados", example = "ATIVO")
            @RequestParam AgenteStatus status){

        return ResponseEntity.ok(this.agenteService.buscarAgentesPorStatus(status));
    }

//Cadastrar novo Agente:
    @Operation(
            summary = "Cadastrar novo Agente",
            description = "Cadastra um novo Agente no banco, validando os dados de entrada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @PostMapping
    public ResponseEntity<AgenteResponseDTO> cadastrarNovoAgente(@RequestBody @Valid AgenteRequestDTO novoAgenteDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.agenteService.cadastrarNovoAgente(novoAgenteDTO));
    }

//Atualizar:
    @Operation(
            summary = "Atualizar um Agente",
            description = "Atualiza os dados de um Agente existente com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @PutMapping("/{agenteId}")
    public ResponseEntity<AgenteResponseDTO> atualizarAgente(
            @Parameter(description = "ID do Agente a ser atualizado", example = "2")
            @PathVariable Long agenteId,
            @RequestBody @Valid AgenteRequestDTO atualizado){

        return ResponseEntity.ok(this.agenteService.atualizarAgente(agenteId,atualizado));
    }

//Desativar Agente:
    @Operation(
            summary = "Desativar um Agente",
            description = "Atualiza o campo 'status' do Agente para DESATIVADO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do Agente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado"),
    })
    @PutMapping("/{agenteId}/desativar")
    public ResponseEntity<AgenteResumoResponseDTO> desativarAgente(
            @Parameter(description = "ID do Agente que será DESATIVADO", example = "2")
            @PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.desativarAgente(agenteId));
    }


//Ativar Agente:
    @Operation(
            summary = "Ativar um Agente",
            description = "Atualiza o campo 'status' do Agente para ATIVADO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do Agente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado"),
    })
    @PutMapping("/{agenteId}/ativar")
    public ResponseEntity<AgenteResumoResponseDTO> ativarAgente(
            @Parameter(description = "ID do Agente que será ATIVADO", example = "2")
            @PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.ativarAgente(agenteId));
    }

////Deletar por id: SÓ PARA TESTES
//    @DeleteMapping("/{agenteId}")
//    public ResponseEntity<Void> deletarAgente(@PathVariable Long agenteId){
//        return ResponseEntity.noContent().build(); //204
//    }
}
