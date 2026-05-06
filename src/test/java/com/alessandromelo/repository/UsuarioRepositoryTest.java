package com.alessandromelo.repository;

import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Usuario;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;


@DataJpaTest
class UsuarioRepositoryTest {

    private final UsuarioRepository usuarioRepository;
    private final TestEntityManager testEntityManager;


    @Autowired
    public UsuarioRepositoryTest(UsuarioRepository usuarioRepository, TestEntityManager testEntityManager) {
        this.usuarioRepository = usuarioRepository;
        this.testEntityManager = testEntityManager;
    }


    @Test
    @DisplayName("existByEmail deve retornar true")
    void existsByEmailDeveRetornarTrue(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByEmail("mariazinha25@gmail.com");

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existByEmail deve retornar false")
    void existsByEmailDeveRetornarFalse(){
        //Arrange:
        //Act:
        boolean retorno = this.usuarioRepository.existsByEmail("mariazinha25@gmail.com");

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("existByMatricula deve retornar true")
    void existsByMatriculaDeveRetornarTrue(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByMatricula("67694");

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existByMatricula deve retornar false")
    void existsByMatriculaDeveRetornarFalse(){
        //Arrange:
        //Act:
        boolean retorno = this.usuarioRepository.existsByMatricula("67694");

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("existsByEmailAndIdNot deve retornar true")
    void existsByEmailAndIdNotDeveRetornarTrue(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByEmailAndIdNot("mariazinha25@gmail.com", 999L);

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsByEmailAndIdNot deve retornar false")
    void existsByEmailAndIdNotDeveRetornarFalse(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByEmailAndIdNot("joaogames69@gmail.com", 999L);

        //Assert:
        Assertions.assertFalse(retorno);
    }

    @Test
    @DisplayName("existsByMatriculaAndIdNot deve retornar true")
    void existsByMatriculaAndIdNotDeveRetornarTrue(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByMatriculaAndIdNot("67694", 999L);

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsByMatriculaAndIdNot deve retornar false")
    void existsByMatriculaAndIdNotDeveRetornarFalse(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByEmailAndIdNot("76555", 999L);

        //Assert:
        Assertions.assertFalse(retorno);
    }

    @Test
    @DisplayName("existsByDepartamentoId deve retornar true")
    void existsByDepartamentoIdDeveRetornarTrue(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setNome("RH");
        this.testEntityManager.persistAndFlush(departamento);

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        usuario.setDepartamento(departamento);
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByDepartamentoId(1L);

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsByDepartamentoId deve retornar false")
    void existsByDepartamentoIdDeveRetornarFalse(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setNome("RH");
        this.testEntityManager.persistAndFlush(departamento);

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        usuario.setDepartamento(departamento);
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        boolean retorno = this.usuarioRepository.existsByDepartamentoId(7L); //Id que não existe

        //Assert:
        Assertions.assertFalse(retorno);
    }

    @Test
    @DisplayName("findByDepartamentoId deve retornar lista com Usuarios")
    void findByDepartamentoIdDeveRetornarListaDeUsuarios(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setNome("RH");
        this.testEntityManager.persistAndFlush(departamento);

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        usuario.setDepartamento(departamento);
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        List<Usuario> retorno = this.usuarioRepository.findByDepartamentoId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals("Maria", retorno.get(0).getNome()),
                () -> Assertions.assertEquals("mariazinha25@gmail.com",retorno.get(0).getEmail()),
                () -> Assertions.assertEquals("67694",retorno.get(0).getMatricula())
        );
    }

    @Test
    @DisplayName("findByDepartamentoId deve retornar lista vazia")
    void findByDepartamentoIdDeveRetornarListaVazia(){
        //Arrange:
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("mariazinha25@gmail.com");
        usuario.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario);

        //Act:
        List<Usuario> retorno = this.usuarioRepository.findByDepartamentoId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }


//Constraints:
    @Test
    @DisplayName("Não deve salvar quando possuir email duplicado")
    void naoDeveSalvarQuandoPossuirEmailDuplicado(){
        //Arrange:
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Maria");
        usuario1.setEmail("mariazinha25@gmail.com");
        usuario1.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Maria Eduarda");
        usuario2.setEmail("mariazinha25@gmail.com");
        usuario2.setMatricula("22801");

        //Act:
        //Assert:
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> this.testEntityManager.persistAndFlush(usuario2));

    }

    @Test
    @DisplayName("Não deve salvar quando possuir matricula duplicada")
    void naoDeveSalvarQuandoPossuirMatriculaDuplicada(){
        //Arrange:
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Maria");
        usuario1.setEmail("mariazinha25@gmail.com");
        usuario1.setMatricula("67694");
        this.testEntityManager.persistAndFlush(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Maria Eduarda");
        usuario2.setEmail("dudinha77@gmail.com");
        usuario2.setMatricula("67694");

        //Act:
        //Assert:
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> this.testEntityManager.persistAndFlush(usuario2));

    }

}