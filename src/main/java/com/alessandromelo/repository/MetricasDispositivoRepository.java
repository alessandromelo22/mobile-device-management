package com.alessandromelo.repository;

import com.alessandromelo.entity.MetricasDispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricasDispositivoRepository extends JpaRepository<MetricasDispositivo, Long> {



    List<MetricasDispositivo> findByAgenteId(Long agenteId);

}
