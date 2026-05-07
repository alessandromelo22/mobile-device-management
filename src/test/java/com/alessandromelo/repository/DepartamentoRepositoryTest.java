package com.alessandromelo.repository;

import com.alessandromelo.entity.Departamento;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


/**
    Testes da camada DepartamentoRepository

 */
@DataJpaTest
class DepartamentoRepositoryTest {

    private final DepartamentoRepository departamentoRepository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public DepartamentoRepositoryTest(DepartamentoRepository departamentoRepository, TestEntityManager testEntityManager){
        this.departamentoRepository = departamentoRepository;
        this.testEntityManager = testEntityManager;
    }



//Query Methods
    @Test
    @DisplayName("existsByNome deve retornar true")
    void existsByNomeDeveRetornarTrue(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsByNome("Recursos Humanos");

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsByNome deve retornar false")
    void existsByNomeDeveRetornarFalse(){
        //Arrange:
        //Act:
        boolean retorno = this.departamentoRepository.existsByNome("Financeiro");

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("existsBySigla deve retornar true")
    void existsBySiglaDeveRetornarTrue(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsBySigla("RH");

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsBySigla deve retornar false")
    void existsBySiglaDeveRetornarFalse(){
        //Arrange:
        //Act:
        boolean retorno = this.departamentoRepository.existsBySigla("FIN");

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("existsByNomeAndIdNot deve retornar true")
    void existsByNomeAndIdNotDeveRetornarTrue(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsByNomeAndIdNot("Recursos Humanos", 999L);

        //Assert:
        Assertions.assertTrue(retorno);
    }


    @Test
    @DisplayName("existsByNomeAndIdNot deve retornar false")
    void existsByNomeAndIdNotDeveRetornarFalse(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsByNomeAndIdNot("Financeiro", 999L);

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("existsBySiglaAndIdNot deve retornar true")
    void existsBySiglaAndIdNotDeveRetornarTrue(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsBySiglaAndIdNot("RH", 999L);

        //Assert:
        Assertions.assertTrue(retorno);
    }

    @Test
    @DisplayName("existsBySiglaAndIdNot deve retornar false")
    void existsBySiglaAndIdNotDeveRetornarFalse(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        boolean retorno = this.departamentoRepository.existsBySiglaAndIdNot("FIN", 999L);

        //Assert:
        Assertions.assertFalse(retorno);
    }


    @Test
    @DisplayName("findByNome deve retornar Departamento")
    void findByNomeDeveRetornarDepartamento(){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");
        this.testEntityManager.persistAndFlush(departamento);

        //Act:
        Departamento retorno = this.departamentoRepository.findByNome("Recursos Humanos");

        //Assert:
        Assertions.assertNotNull(retorno);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Recursos Humanos", departamento.getNome()),
                () -> Assertions.assertEquals("RH", departamento.getSigla())
        );
    }


//Constraints:

    @Test
    @DisplayName("Não deve salvar quando possuir nome duplicado")
    void naoDeveSalvarQuandoPossuirNomeDuplicado(){
        //Arrange:
        Departamento dp1 = new Departamento();
        dp1.setNome("Recursos Humanos");
        dp1.setSigla("RH");
        this.testEntityManager.persistAndFlush(dp1);

        Departamento dp2 = new Departamento();
        dp2.setNome("Recursos Humanos");
        dp2.setSigla("ReH");

        //Act:
        //Assert:
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> this.testEntityManager.persistAndFlush(dp2));

    }

    @Test
    @DisplayName("Não deve salvar quando possuir sigla duplicada")
    void naoDeveSalvarQuandoPossuirSiglaDuplicado(){
        //Arrange:
        Departamento dp1 = new Departamento();
        dp1.setNome("Recursos Humanos");
        dp1.setSigla("RH");
        this.testEntityManager.persistAndFlush(dp1);

        Departamento dp2 = new Departamento();
        dp2.setNome("Recursos Humanos 2");
        dp2.setSigla("RH");

        //Act:
        //Assert:
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> this.testEntityManager.persistAndFlush(dp2));

    }
}