package com.alessandromelo.repository;

import com.alessandromelo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByMatriculaAndIdNot(String matricula,Long id);


    //verifica se tem algum Usuario associado ao Departamento passado pelo Id
    boolean existsByDepartamentoId(Long departamentoId);


}
