package com.alessandromelo.controller;

import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioDispositivoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioRequestDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Usuario", description = "Operações voltadas para o gerenciamento de Usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



//ListarUsuarios:
    @Operation(
            summary = "Listar todos os Usuarios",
            description = "Retorna uma lista com todos os Usuarios salvos no banco")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.ok(this.usuarioService.listarUsuarios());
    }


//Buscar Usuario por Id:
    @Operation(
            summary = "Buscar Usuario pelo ID",
            description = "Retorna um Usuario específico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(
            @Parameter(description = "ID do Usuario a ser buscado", example = "2")
            @PathVariable Long usuarioId){

        return ResponseEntity.ok(this.usuarioService.buscarUsuarioPorId(usuarioId));

    }

    
//Cadastrar novo Usuario:
    @Operation(
            summary = "Cadastrar novo Usuario",
            description = "Cadastra um novo Usuario no banco, validando os dados de entrada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarNovoUsuario(@RequestBody @Valid UsuarioRequestDTO novoUsuarioDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usuarioService.cadastrarNovoUsuario(novoUsuarioDTO));
    }


//Atualizar Usuario:
    @Operation(
            summary = "Atualizar um Usuario",
            description = "Atualiza os dados de um Usuario existente com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @Parameter(description = "ID do Usuario a ser atualizado", example = "2")
            @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioRequestDTO usuarioAtualizadoDTO){

        return ResponseEntity.ok(this.usuarioService.atualizarUsuario(usuarioId, usuarioAtualizadoDTO));
    }


//Remover Usuario por Id:
    @Operation(
            summary = "Excluir um Usuario",
            description = "Exclui um Usuario existente com base no ID informado, sendo feitas validações de relacionamento antes de concretizar a operação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "409", description = "Exclusão não realizada - Usuario está vinculado a outro registro")
    })
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> removerUsuarioPorId(
            @Parameter(description = "ID do Usuario a ser excluido", example = "2")
            @PathVariable Long usuarioId){

        this.usuarioService.removerUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build(); //204
    }


//Listar Dispositivos cadastrados em um determinado Usuario:
    @Operation(
            summary = "Listar Dispositivos de um Usuario",
            description = "Retorna uma lista dos Dispositivos vinculados a um determinado Usuario com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    @GetMapping("/{usuarioId}/dispositivos")
    public ResponseEntity<List<DispositivoResumoResponseDTO>> listarDispositivosVinculadosAoUsuario(
            @Parameter(description = "ID do Usuario a ser consultado", example = "2")
            @PathVariable Long usuarioId){

        return ResponseEntity.ok(this.usuarioService.listarDispositivosVinculadosAoUsuario(usuarioId));
    }


//Setar Dispositivo a um Usuario:
    @Operation(
            summary = "Vincular um Dispositivo a um Usuario",
            description = "Vincula um Dispositivo existente a um Usuario, com base nos IDs informados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo vinculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @PutMapping("/{usuarioId}/dispositivos/{dispositivoId}")
    public ResponseEntity<UsuarioDispositivoResponseDTO> vincularDispositivoAoUsuario(
            @Parameter(description = "ID do Usuario que recebera um Dispositivo", example = "2")
            @PathVariable Long usuarioId,
            @Parameter(description = "ID do Dispositivo a ser vinculado", example = "2")
            @PathVariable Long dispositivoId){

        return ResponseEntity.ok(this.usuarioService.vincularDispositivoAoUsuario(usuarioId,dispositivoId));
    }


//Setar Usuario a um Departamento:
    @Operation(
            summary = "Vincular um Usuario a um Departamento",
            description = "Vincula um Usuario existente a um Departamento, com base nos IDs informados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario vinculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID informado inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{usuarioId}/departamentos/{departamentoId}")
    public ResponseEntity<UsuarioDepartamentoResponseDTO> vincularUsuarioAoDepartamento(
            @Parameter(description = "ID do Usuario a ser vinculado", example = "2")
            @PathVariable Long usuarioId,
            @Parameter(description = "ID do Departamento que recebera um Usuario", example = "2")
            @PathVariable Long departamentoId){

        return ResponseEntity.ok(this.usuarioService.vincularUsuarioAoDepartamento(usuarioId, departamentoId));
    }



    @Operation(
            summary = "Cadastra múltiplos Usuários a partir de um arquivo CSV",
            description = "Este endpoint permite a importação em lote de usuários a partir de um arquivo CSV.\n" +
                    "O arquivo é validado quanto à estrutura e aos campos obrigatórios antes do processamento.\n" +
                    "Em caso de inconsistências no formato ou nos dados, a importação é interrompida e uma mensagem de erro é retornada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuários cadastrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Problemas na estrutra do arquivo CSV enviado: " +
                    "arquivo não enviado, vazio, arquivo com tipo inválido ou com tamanho máximo excedido, cabeçalho inválido, etc. "),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado!"),
            @ApiResponse(responseCode = "422", description = "Problemas no conteúdo do arquivo: campos obrigatórios faltando, " +
                    "incompatibilidade de tipos, erros de parsing, etc.")
    })
    @PostMapping("/import")
    public ResponseEntity<Long> cadastrarUsuariosCsv(
            @Parameter(description = "Arquivo que contém os registros dos Usuários que serão registrados")
            @RequestParam(name = "file") MultipartFile arquivo){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.usuarioService.cadastrarUsuariosCsv(arquivo));
    }




}
