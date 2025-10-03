package com.alessandromelo.repository;

import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgenteRepository extends JpaRepository<Agente, Long> {

    //Aparentemente nao precisa criar métodos de validação, Agente não tem campos únicos

    //Verificar se precisa de validação para campos não nulos

    //verifica se existe algum Agente associado ao Dispositivo passado pelo ID
    boolean existsByDispositivoId(Long dispositivoId);

    //Busca todos os Agentes com um status específico
    List<Agente> findByStatus(AgenteStatus status);

}
