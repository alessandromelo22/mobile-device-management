package com.alessandromelo.service;

import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.exception.departamento.DepartamentoNaoEncontradoException;
import com.alessandromelo.exception.departamento.NomeJaCadastradoException;
import com.alessandromelo.exception.departamento.SiglaJaCadastradaException;
import com.alessandromelo.mapper.DepartamentoMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private DepartamentoMapper departamentoMapper;

    @InjectMocks
    private DepartamentoService departamentoService;


    //Teste unitario deve garantir a lógica do metodo e não a arquitetura

    /**<p><b>listarTodosDepartamentos():</b></p>
     *
     *  <p>1- Deve chamar o metodo findAll()</p>
     *  <p>2- Deve chamar o metodo toResponseDTO() para cada item da lista de Departamento!!!!!</p>
     *  <p>3- Deve retornar uma lista de DepartamentoResponseDTO vindo do metodo toResponseDTO()</p>
     */


    @Test
    @DisplayName("listarTodosDepartamentos() deve chamar findAll()")
    void listarTodosDepartamentosDeveChamarFindAll(){

        //Arrange:

        //Act:
        this.departamentoService.listarTodosDepartamentos();

        //Assert:
        verify(this.departamentoRepository).findAll();
    }

    //Verificar se compensa deixar esse teste, pois aparentemente testa a arquitetura
    // e testes unitarios devem testar o comportamento (logica)
    @Test
    @DisplayName("listarTodosDepartamentos() deve chamar método toResponseDTO()")
    void listarTodosDepartamentosDeveChamarToResponseDTO (){

        //Arrange:
        Departamento departamento01 = new Departamento();
        departamento01.setId(1L);
        departamento01.setNome("Recursos Humanos");
        departamento01.setSigla("RH");

        Departamento departamento02 = new Departamento();
        departamento02.setId(2L);
        departamento02.setNome("Compras");
        departamento02.setSigla("Cp");

        Mockito.when(this.departamentoRepository.findAll()).thenReturn(List.of(departamento01, departamento02));

        //Act:
        this.departamentoService.listarTodosDepartamentos();

        //Assert:
        verify(this.departamentoMapper, times(2)).toResponseDTO(any());
    }


    //RetornoDeBuscarTodosDepartamentosDevemVimDeDepartamentoMapper
    @Test
    @DisplayName("listarTodosDepartamentos() deve retornar uma lista de DepartamentosResponseDTO vindo do toResponseDTO()")
    void listarTodosDepartamentosDeveRetornarListaDepartamentoResponseDTO (){
        //Arrange:

        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");


        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Recursos Humanos");
        responseDTO.setSigla("RH");

        when(this.departamentoRepository.findAll()).thenReturn(List.of(departamento));
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);

        //Act:
        List<DepartamentoResponseDTO> retorno = this.departamentoService.listarTodosDepartamentos();

        //Assert:
        Assertions.assertSame(responseDTO, retorno.get(0));
        verify(this.departamentoMapper).toResponseDTO(departamento);
        //O service devolveu exatamente a MESMA instância que o mapper retornou?

        //procurar saber se é valido validar o tamanho da lista, ou não, se estrapola a responsabilidade do Service
    }

    @Test
    void listarTodosDepartamentosDeveRetornarUmaListaVazia (){
        //Arrange:
        when(this.departamentoRepository.findAll()).thenReturn(List.of());


        //Act:
        List<DepartamentoResponseDTO> retorno = this.departamentoService.listarTodosDepartamentos();

        //Assert:
        Assertions.assertTrue(retorno.isEmpty());
        //Procurar saber se faz sentido mockar aquele metodo findAll() ou não
    }





    /**<p><b>buscarDepartamentoPorId():</b></p>
     *
     *  <p>1- Deve chamar o metodo findById()</p>
     *  <p>2- Deve chamar o metodo toResponseDTO()</p>
     *  <p>3- Deve retornar um DepartamentoResponseDTO vindo do toResponseDTO()</p>
     */

    @Test
    @DisplayName("buscarDepartamentoPorId() deve chamar método toReponseDTO()")
    void buscarDepartamentoPorIdDeveChamarToResponseDTO (){

        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");

        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Recursos Humanos");
        responseDTO.setSigla("RH");

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);


        //Act:
        this.departamentoService.buscarDepartamentoPorId(1L);

        //Assert:
        verify(this.departamentoMapper).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarDepartamentoPorId() deve lançar DepartamentoNaoEncontradoException")
    void buscarDepartamentoPorIdDeveLancarDepartamentoNaoEncontradoException (){
        //Arrange:
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> this.departamentoService.buscarDepartamentoPorId(1L));

        verify(this.departamentoMapper, never()).toResponseDTO(any());
    }





    @Test
    @DisplayName("criarNovoDepartamento() deve chamar método save()")
    void criarNovoDepartamentoDeveChamarSave (){
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTO();
        requestDTO.setNome("Recursos Humanos");
        requestDTO.setSigla("RH");

        Departamento departamento = new Departamento();
        departamento.setNome("Recursos Humanos");
        departamento.setSigla("RH");

        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Recursos Humanos");
        responseDTO.setSigla("RH");

        when(this.departamentoRepository.existsByNome(requestDTO.getNome())).thenReturn(false);
        when(this.departamentoRepository.existsBySigla(requestDTO.getSigla())).thenReturn(false);
        when(this.departamentoMapper.toEntity(requestDTO)).thenReturn(departamento);
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);
        when(this.departamentoRepository.save(departamento)).thenReturn(departamento);
        //Dar uma olhada nesse ultimo mock

        //Act:
        this.departamentoService.criarNovoDepartamento(requestDTO);

        //Assert:
        verify(this.departamentoRepository).save(any());
    }



    @Test
    @DisplayName("cadastrarDepartamento() deve lançar Exception NomeJaCadastradoException")
    void criarNovoDepartamentoDeveLancarNomeJaCadastradoException(){

        //Arrange
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTO();
        requestDTO.setNome("Recursos Humanos");
        requestDTO.setSigla("RH");

        Mockito.when(departamentoRepository.existsByNome(requestDTO.getNome())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(NomeJaCadastradoException.class,
                () -> departamentoService.criarNovoDepartamento(requestDTO));

        Mockito.verify(this.departamentoRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarDepartamento() deve lançar Exception SiglaJaCadastradaException")
    void criarNovoDepartamentoDeveLancarSiglaJaCadastradaException(){

        //Arrange
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTO();
        requestDTO.setNome("Recursos Humanos");
        requestDTO.setSigla("RH");

        Mockito.when(departamentoRepository.existsBySigla(requestDTO.getSigla())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(SiglaJaCadastradaException.class,
                () -> departamentoService.criarNovoDepartamento(requestDTO));


        Mockito.verify(this.departamentoRepository,never()).save(any());
    }
}