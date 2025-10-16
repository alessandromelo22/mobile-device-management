package com.alessandromelo.controller;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.service.ComandoService;
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

@Tag(name = "Comando", description = "Operações voltadas para o gerenciamento de Comandos")
@RestController
@RequestMapping("/comandos")
public class ComandoController {

    private ComandoService comandoService;



    public ComandoController(ComandoService comandoService) {
        this.comandoService = comandoService;
    }



    //Verificar se é viavel manter esse endpoint
    @GetMapping
    public ResponseEntity<List<ComandoResponseDTO>> buscarTodosComandos(){
        return ResponseEntity.ok(this.comandoService.buscarTodosComandos());
    }

    @Operation(
            summary = "Buscar Comandos pelo status",
            description = "Retorna uma lista com os Comandos que possuem um status específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comandos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status informado inválido")
    })
    @GetMapping("/status")
    public ResponseEntity<List<ComandoResponseDTO>> buscarComandosPorStatus(
            @Parameter(description = "Status dos Comandos a serem buscados", example = "PENDENTE")
            @RequestParam ComandoStatus status){

        return ResponseEntity.ok(this.comandoService.buscarComandosPorStatus(status));
    }


    @Operation(
            summary = "Buscar Comando pelo ID",
            description = "Retorna um Comando específico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comando retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido."),
            @ApiResponse(responseCode = "404", description = "Comando não encontrado")
    })
    @GetMapping("/{comandoId}")
    public ResponseEntity<ComandoResponseDTO> buscarComandoPorId(
            @Parameter(description = "ID do Comando a ser buscado", example = "2")
            @PathVariable Long comandoId){

        return ResponseEntity.ok(this.comandoService.buscarComandoPorId(comandoId));
    }


    @Operation(
            summary = "Cadastrar novo Comando",
            description = "Cadastra um novo Comando no banco, validando os dados de entrada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comando cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    @PostMapping
    public ResponseEntity<ComandoResponseDTO> criarComando(@RequestBody @Valid ComandoRequestDTO novoComandoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(this.comandoService.criarComando(novoComandoDTO));
    }


////TEMPORÁRIO: SÓ PARA TESTES
//    @DeleteMapping("/{comandoId}")
//    public ResponseEntity<Void> deletarComando(@PathVariable Long comandoId){
//        this.comandoService.deletarComando(comandoId);
//        return ResponseEntity.noContent().build();
//    }
}
