package com.alessandromelo.controller;

import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;
import com.alessandromelo.service.DispositivoService;
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


@Tag(name = "Dispositivo", description = "Operações voltadas para o gerenciamento de Dispositivos")
@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    private DispositivoService dispositivoService;


    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }


//Listar todos os Dispositivos:
    @Operation(
            summary = "Listar todos os Dispositivos",
            description = "Retorna uma lista com todos os Dispositivos salvos no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivos retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<DispositivoResponseDTO>> listarTodosDispositivos(){
        return ResponseEntity.ok(this.dispositivoService.listarTodosDispositivos());
    }

//Buscar dispositivo por Id:
    @Operation(
            summary = "Buscar Dispositivo por ID",
            description = "Retorna uma Dispositivo específico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @GetMapping("/{dispositivoId}")
    public ResponseEntity<DispositivoResponseDTO> buscarDispositivoPorId(
            @Parameter(description = "ID do Dispositivo a ser buscado", example = "2")
            @PathVariable Long dispositivoId){

        return ResponseEntity.ok(this.dispositivoService.buscarDispositivoPorId(dispositivoId));
    }


//Buscar Dispositivos por status:
    @Operation(
            summary = "Buscar Dispositivos pelo status",
            description = "Retorna uma lista com os Dispositivos que possuem um status específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status informado inválido")
    })
    @GetMapping("/status")
    public ResponseEntity<List<DispositivoResponseDTO>> buscarDispositivosPorStatus(
            @Parameter(description = "Status dos Dispositivos a serem buscados", example = "ATIVO")
            @RequestParam DispositivoStatus status){

        return ResponseEntity.ok(this.dispositivoService.buscarDispositivosPorStatus(status));
    }


//Cadastrar Dispositivo:
    @Operation(
            summary = "Cadastrar novo Dispositivo",
            description = "Cadastra um novo Dispositivo no banco, validando os dados de entrada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dispositivo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @PostMapping
    public ResponseEntity<DispositivoResponseDTO> cadastrarNovoDispositivo(@RequestBody @Valid DispositivoRequestDTO novoDispositivoDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.dispositivoService.cadastrarNovoDispositivo(novoDispositivoDTO));
    }

//Atualizar Dispositivo:
    @Operation(
            summary = "Atualizar um Dispositivo",
            description = "Atualiza os dados de um Dispositivo existente com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @PutMapping("/{dispositivoId}")
    public ResponseEntity<DispositivoResponseDTO> atualizarDispositivo(
            @Parameter(description = "ID do Dispositivo a ser atualizado", example = "3")
            @PathVariable Long dispositivoId,
            @RequestBody @Valid DispositivoRequestDTO atualizado){

        return ResponseEntity.ok(this.dispositivoService.atualizarDispositivo(dispositivoId, atualizado));
    }

//Remover Dispositivo por id:
    @Operation(
            summary = "Excluir um Dispositivo",
            description = "Exclui um Dispositivo existente com base no ID informado, sendo feitas validações de relacionamento antes de concretizar a operação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado"),
            @ApiResponse(responseCode = "409", description = "Exclusão não realizada - Dispositivo está vinculado a outro registro")
    })
    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<Void> removerDispositivoPorId(
            @Parameter(description = "ID do Dispositivo a ser excluído", example = "3")
            @PathVariable Long dispositivoId){

        this.dispositivoService.removerDispositivoPorId(dispositivoId);
        return ResponseEntity.noContent().build(); //204
    }


}
