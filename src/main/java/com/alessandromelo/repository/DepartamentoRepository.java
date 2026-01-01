package com.alessandromelo.repository;

import com.alessandromelo.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    boolean existsByNome(String nome);
    boolean existsBySigla(String sigla);

    boolean existsByNomeAndIdNot(String nome, Long id);
    boolean existsBySiglaAndIdNot(String sigla, Long id);

    //retorna o Id do Departamento com base no nome informado (ACHO QUE NÃO VAI SER USADO)
    Long findIdByNome(String nome);


    /**
     * Busca no banco um Departamento com o nome passado como argumento
     *
     * @param nome nome do Departamento a ser retornado
     * @return Entidade de Departamento
     */
    Departamento findByNome(String nome);
}
