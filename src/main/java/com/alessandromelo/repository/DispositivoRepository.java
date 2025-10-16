package com.alessandromelo.repository;

import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.enums.DispositivoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    //Retorna um boolean se o numeroSerie já existe no banco
    boolean existsByNumeroSerie(String numeroSerie);

    //Retorna um boolean se o numeroSerie de um dispositivo especifico existe no banco
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);


    //Verifica se existe algum Dispositivo associado ao Usuario passado pelo ID:
    boolean existsByUsuarioId(Long usuarioId);

    //Retorna uma lista contendo os Dispositivos com um status específico
    List<Dispositivo> findByStatus(DispositivoStatus status);
}
