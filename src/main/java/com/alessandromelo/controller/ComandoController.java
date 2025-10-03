package com.alessandromelo.controller;

import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.service.ComandoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comandos")
public class ComandoController {

    private ComandoService comandoService;

    public ComandoController(ComandoService comandoService) {
        this.comandoService = comandoService;
    }

    @GetMapping
    public ResponseEntity<List<ComandoResponseDTO>> buscarTodosComandos(){
        return ResponseEntity.ok(this.comandoService.buscarTodosComandos());
    }

    @GetMapping("/status")
    public ResponseEntity<List<ComandoResponseDTO>> buscarComandosPorStatus(@RequestParam ComandoStatus status){
        return ResponseEntity.ok(this.comandoService.buscarComandosPorStatus(status));
    }

    @GetMapping("/{comandoId}")
    public ResponseEntity<ComandoResponseDTO> buscarComandoPorId(@PathVariable Long comandoId){
        return ResponseEntity.ok(this.comandoService.buscarComandoPorId(comandoId));
    }

    @PostMapping
    public ResponseEntity<ComandoResponseDTO> criarComando(@RequestBody @Valid ComandoRequestDTO novoComandoDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.comandoService.criarComando(novoComandoDTO));
    }


////TEMPORÁRIO: SÓ PARA TESTES
//    @DeleteMapping("/{comandoId}")
//    public ResponseEntity<Void> deletarComando(@PathVariable Long comandoId){
//        this.comandoService.deletarComando(comandoId);
//        return ResponseEntity.noContent().build();
//    }
}
