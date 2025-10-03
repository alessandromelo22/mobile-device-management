package com.alessandromelo.controller;


import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.service.AgenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Operações CRUD de Agente, operações essas que serão chamadas
 * por algum adminstrador e não pelo Agente em sí.
 */
@RestController
@RequestMapping("/agentes")
public class AgenteController {

    private AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

//Buscar todos:
    @GetMapping
    public ResponseEntity<List<AgenteResponseDTO>> buscarTodosAgentes(){
       return ResponseEntity.ok(this.agenteService.listarTodosAgentes());
    }

//Buscar por Id
    @GetMapping("/{agenteId}")
    public ResponseEntity<AgenteResponseDTO> buscarAgentePorId(@PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.buscarAgentePorId(agenteId));
    }

//Buscar por status:
    @GetMapping("/status")
    public ResponseEntity<List<AgenteResponseDTO>> buscarAgentesPorStatus(@RequestParam AgenteStatus status){
        return ResponseEntity.ok(this.agenteService.buscarAgentesPorStatus(status));
    }

//Cadastrar novo Agente:
    @PostMapping
    public ResponseEntity<AgenteResponseDTO> cadastrarNovoAgente(@RequestBody @Valid AgenteRequestDTO novoAgenteDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.agenteService.cadastrarNovoAgente(novoAgenteDTO));
    }

//Atualizar:
    @PutMapping("/{agenteId}")
    public ResponseEntity<AgenteResponseDTO> atualizarAgente(@PathVariable Long agenteId,
                                                             @RequestBody @Valid AgenteRequestDTO atualizado){

        return ResponseEntity.ok(this.agenteService.atualizarAgente(agenteId,atualizado));
    }

//Desativar Agente:
    @PutMapping("/{agenteId}/desativar")
    public ResponseEntity<AgenteResumoResponseDTO> desativarAgente(@PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.desativarAgente(agenteId));
    }


//Ativar Agente:
    @PutMapping("/{agenteId}/ativar")
    public ResponseEntity<AgenteResumoResponseDTO> ativarAgente(@PathVariable Long agenteId){
        return ResponseEntity.ok(this.agenteService.ativarAgente(agenteId));
    }


////Deletar por id: SÓ PARA TESTES
//    @DeleteMapping("/{agenteId}")
//    public ResponseEntity<Void> deletarAgente(@PathVariable Long agenteId){
//        return ResponseEntity.noContent().build(); //204
//    }
}
