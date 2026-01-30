package com.alessandromelo.repository;

import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComandoRepository extends JpaRepository<Comando, Long> {

    //retorna todos os comandos de um Status especifico de um Agente especifico, ordenados pela data de criação em ordem crescente
    List<Comando> findByAgenteIdAndStatusOrderByDataCriacaoAsc(Long agenteId, ComandoStatus status);

    //retorna todos os comandos de um Status especifico
    List<Comando> findByStatus(ComandoStatus status);

    //retorna todos os comandos de um agente específico
    List<Comando> findByAgenteId(Long agenteId);
}
