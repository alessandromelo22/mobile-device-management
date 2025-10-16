package com.alessandromelo.controller;

import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.service.DepartamentoService;
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

@Tag(name = "Departamento", description = "Operações voltadas para o gerenciamento dos Departamentos")
@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private DepartamentoService departamentoService;


    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }



    @Operation(
            summary = "Listar todos os Departamentos",
            description = "Retorna uma lista com todos os Departamentos salvos no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamentos retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> listarTodosDepartamentos(){
        return ResponseEntity.ok(this.departamentoService.listarTodosDepartamentos());
    }


    @Operation(
            summary = "Buscar Departamento pelo ID",
            description = "Retorna um Departamento específico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{departamentoId}")
    public ResponseEntity<DepartamentoResponseDTO> buscarDepartamentoPorId(
            @Parameter(description = "ID do Departamento a ser buscado", example = "2")
            @PathVariable Long departamentoId){

        return ResponseEntity.ok(this.departamentoService.buscarDepartamentoPorId(departamentoId));
    }


    @Operation(
            summary = "Cadastrar novo Departamento",
            description = "Cadastra um novo Departamento no banco, validando os dados de entrada ")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> criarNovoDepartamento(@RequestBody @Valid DepartamentoRequestDTO novoDepartamentoDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.departamentoService.criarNovoDepartamento(novoDepartamentoDTO));
    }


//Atualizar Departamento:
    @Operation(
            summary = "Atualizar um Departamento",
            description = "Atualiza os dados de um Departamento existente com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{departamentoId}")
    public ResponseEntity<DepartamentoResponseDTO> atualizarDepartamento(
            @Parameter(description = "ID do Departamento a ser atualizado", example = "2")
            @PathVariable Long departamentoId,
            @RequestBody @Valid DepartamentoRequestDTO departamentoAtualizadoDTO){

        return ResponseEntity.ok(this.departamentoService.atualizarDepartamento(departamentoId, departamentoAtualizadoDTO));
    }


//Remover Departamento por id:
    @Operation(
            summary = "Excluir um Departamento",
            description = "Exclui um Departamento existente com base no ID informado, sendo feitas validações de relacionamento antes de concretizar a operação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Exclusão não realizada - Departamento está vinculado a outro registro")
    })
    @DeleteMapping("/{departamentoId}")
    public ResponseEntity<Void> removerDepartamentoPorId(
            @Parameter(description = "ID do Departamento a ser excluído", example = "2")
            @PathVariable Long departamentoId){

        this.departamentoService.removerDepartamentoPorId(departamentoId);
        return ResponseEntity.noContent().build(); //204
    }


//Listar Usuarios que pertencem a um Departamento:
    @Operation(
            summary = "Listar Usuarios pertencentes a um Departamento",
            description = "Retorna uma lista dos Usuários que pertencem a um Departamento com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{departamentoId}/usuarios")
    public ResponseEntity<List<UsuarioResumoResponseDTO>> listarUsuariosDoDepartamento(
            @Parameter(description = "ID do Departamento a ser consultado", example = "2")
            @PathVariable Long departamentoId){

        return ResponseEntity.ok(this.departamentoService.listarUsuariosDoDepartamento(departamentoId));
    }
}
