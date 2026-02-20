package com.alessandromelo.service;

import com.alessandromelo.builders.departamento.DepartamentoBuilder;
import com.alessandromelo.builders.departamento.DepartamentoRequestDTOBuilder;
import com.alessandromelo.builders.departamento.DepartamentoResponseDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioBuilder;
import com.alessandromelo.builders.usuario.UsuarioResumoResponseDTOBuilder;
import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.exception.departamento.DepartamentoNaoEncontradoException;
import com.alessandromelo.exception.departamento.NomeJaCadastradoException;
import com.alessandromelo.exception.departamento.SiglaJaCadastradaException;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.mapper.DepartamentoMapper;
import com.alessandromelo.mapper.UsuarioMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private DepartamentoMapper departamentoMapper;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Captor
    private ArgumentCaptor<Departamento> departamentoCaptor;

    //Teste unitario deve garantir a lógica do metodo e não a arquitetura




    /**<p><b>listarTodosDepartamentos():</b></p>
     *
     *  <p>1- Deve retornar uma lista de DepartamentoResponseDTO</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     */
    @Test
    @DisplayName("listarTodosDepartamentos() deve retornar uma lista de DepartamentosResponseDTO")
    void listarTodosDepartamentosDeveRetornarListaDepartamentoResponseDTO (){
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();
        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTOBuilder().build();

        when(this.departamentoRepository.findAll()).thenReturn(List.of(departamento));
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);

        //Act:
        List<DepartamentoResponseDTO> retorno = this.departamentoService.listarTodosDepartamentos();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Recursos Humanos", retorno.get(0).getNome()),
                () -> Assertions.assertEquals("RH", retorno.get(0).getSigla())
        );
        verify(this.departamentoMapper, times(1)).toResponseDTO(departamento);
    }

    @Test
    @DisplayName("listarTodosDepartamento() deve retornar uma lista vazia")
    void listarTodosDepartamentosDeveRetornarUmaListaVazia (){
        //Arrange:
        when(this.departamentoRepository.findAll()).thenReturn(List.of());

        //Act:
        List<DepartamentoResponseDTO> retorno = this.departamentoService.listarTodosDepartamentos();

        //Assert:
        Assertions.assertTrue(retorno.isEmpty());
        Assertions.assertNotNull(retorno);
    }



    /**<p><b>buscarDepartamentoPorId():</b></p>
     *
     *  <p>1- Deve lançar DepartamentoNaoEncontradoException</p>
     *  <p>2- Deve retornar um DepartamentoResponseDTO vindo do toResponseDTO()</p>
     */
    @Test
    @DisplayName("buscarDepartamentoPorId() deve lançar DepartamentoNaoEncontradoException")
    void buscarDepartamentoPorIdDeveLancarDepartamentoNaoEncontradoException (){
        //Arrange:
        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> this.departamentoService.buscarDepartamentoPorId(1L));

        verify(this.departamentoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarDepartamentoPorId() deve retornar DepartamentoResponseDTO")
    void buscarDepartamentoPorIdDeveRetornarDepartamentoResponseDTO(){
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();
        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTOBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);

        //Act:
        DepartamentoResponseDTO retorno = this.departamentoService.buscarDepartamentoPorId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Recursos Humanos", retorno.getNome()),
                () -> Assertions.assertEquals("RH", retorno.getSigla())
        );
    }



    /**<p><b>criarNovoDepartamento():</b></p>
     *
     *  <p>1-Deve lançar NomeJaCadastradoException </p>
     *  <p>2-Deve lançar SiglaJaCadastradoException </p>
     *  <p>3-Deve retornar DepartamentoResponseDTO </p>
     *
     */
    @Test
    @DisplayName("criarNovoDepartamento() deve lançar NomeJaCadastradoException")
    void criarNovoDepartamentoDeveLancarNomeJaCadastradoException(){
        //Arrange
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();

        Mockito.when(departamentoRepository.existsByNome(requestDTO.getNome())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(NomeJaCadastradoException.class,
                () -> departamentoService.criarNovoDepartamento(requestDTO));

        Mockito.verify(this.departamentoRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("criarNovoDepartamento() deve lançar SiglaJaCadastradaException")
    void criarNovoDepartamentoDeveLancarSiglaJaCadastradaException(){
        //Arrange
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();

        Mockito.when(departamentoRepository.existsBySigla(requestDTO.getSigla())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(SiglaJaCadastradaException.class,
                () -> departamentoService.criarNovoDepartamento(requestDTO));

        Mockito.verify(this.departamentoRepository,never()).save(any());
    }


    @Test
    @DisplayName("criarNovoDepartamento() deve retornar DepartamentoResponseDTO")
    void criarNovoDepartamentoDeveRetornarDepartamentoResponseDTO() {
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();
        Departamento departamento = new DepartamentoBuilder().build();
        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTOBuilder().build();

        when(this.departamentoRepository.existsByNome(requestDTO.getNome())).thenReturn(false);
        when(this.departamentoRepository.existsBySigla(requestDTO.getSigla())).thenReturn(false);
        when(this.departamentoMapper.toEntity(requestDTO)).thenReturn(departamento);
        when(this.departamentoRepository.save(departamento)).thenReturn(departamento);
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);


        //Act:
        DepartamentoResponseDTO retorno = this.departamentoService.criarNovoDepartamento(requestDTO);

        //Assert:
        verify(this.departamentoRepository).save(this.departamentoCaptor.capture());
        Departamento capturado = this.departamentoCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("Recursos Humanos", capturado.getNome()),
                () -> Assertions.assertEquals("RH", capturado.getSigla())
        );

        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Recursos Humanos", retorno.getNome()),
                () -> Assertions.assertEquals("RH", retorno.getSigla())
        );
    }



    /**<p><b>atualizarDepartamento():</b></p>
     *
     *  <p>1-Deve lançar DepartamentoNaoEncontradoException </p>
     *  <p>2-Deve lançar NomeJaCadastradoException </p>
     *  <p>3-Deve lançar SiglaJaCadastrada</p>
     *  <p>4-Deve retornar um DepartamentoResponseDTO</p>
     */
    @Test
    @DisplayName("atualizarDepartamento() deve lançar DepartamentoNaoEncontradoException")
    void atualizarDepartamentoDeveLancarDepartamentoNaoEncontradoException(){
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();
        when(this.departamentoRepository.findById(2L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () ->this.departamentoService.atualizarDepartamento(2L, requestDTO));
        verify(this.departamentoRepository, never()).save(any());
    }


    @Test
    @DisplayName("atualizarDepartamento() deve lançar NomeJaCadastradaException")
    void atualizarDepartamentoDeveLancarNomeJaCadastradoException(){
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();
        Departamento departamento = new DepartamentoBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.departamentoRepository.existsByNomeAndIdNot(requestDTO.getNome(),1L)).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(NomeJaCadastradoException.class,
                () -> this.departamentoService.atualizarDepartamento(1L,requestDTO ));

        verify(this.departamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarDepartamento() deve lançar SiglaJaCadastradaException")
    void atualizarDepartamentoDeveLancarSiglaJaCadastradoException(){
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().build();
        Departamento departamento = new DepartamentoBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.departamentoRepository.existsByNomeAndIdNot(requestDTO.getNome(),1L)).thenReturn(false);
        when(this.departamentoRepository.existsBySiglaAndIdNot(requestDTO.getSigla(), 1L)).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(SiglaJaCadastradaException.class,
                () -> this.departamentoService.atualizarDepartamento(1L,requestDTO ));

        verify(this.departamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarDepartamento() deve retornar um DepartamentoResponseDTO")
    void atualizarDepartamentoDeveRetornarDepartamentoResponseDTO() {
        //Arrange:
        DepartamentoRequestDTO requestDTO = new DepartamentoRequestDTOBuilder().comNome("Compras").comSigla("Cp").build();
        Departamento departamento = new DepartamentoBuilder().build();
        DepartamentoResponseDTO responseDTO = new DepartamentoResponseDTOBuilder().comId(1L).comNome("Compras")
                .comSigla("Cp").build();


        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.departamentoRepository.existsByNomeAndIdNot(requestDTO.getNome(), 1L)).thenReturn(false);
        when(this.departamentoRepository.existsBySiglaAndIdNot(requestDTO.getSigla(), 1L)).thenReturn(false);
        when(this.departamentoRepository.save(departamento)).thenReturn(departamento);
        when(this.departamentoMapper.toResponseDTO(departamento)).thenReturn(responseDTO);

        //Act:
        DepartamentoResponseDTO retorno = this.departamentoService.atualizarDepartamento(1L, requestDTO);

        //Assert:
        verify(this.departamentoRepository,times(1)).save(departamentoCaptor.capture());
        Departamento capturado = departamentoCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("Compras", capturado.getNome()),
                () -> Assertions.assertEquals("Cp", capturado.getSigla())
        );

        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Compras", retorno.getNome()),
                () -> Assertions.assertEquals("Cp", retorno.getSigla())
        );
    }



    /**<p><b>removerDepartamento():</b></p>
     *
     *  <p>1-Deve lançar DepartamentoNaoEncontradoException</p>
     *  <p>2-Deve lançar EntidadeEmUsoException</p>
     *  <p>3-Deve chamar metodo delete()</p>
     */
    @Test
    @DisplayName("removerDepartamento() deve lançar DepartamentoNaoEncontradoException")
    void removerDepartamentoDeveLancarDepartamentoNaoEncontradoException() {
        //Arrange:
        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> this.departamentoService.removerDepartamentoPorId(1L));

        verify(this.departamentoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerDepartamento() deve lançar EntidadeEmUsoException")
    void removerDepartamentoDeveLancarEntidadeEmUsoException() {
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.usuarioRepository.existsByDepartamentoId(1L)).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(EntidadeEmUsoException.class,
                () -> this.departamentoService.removerDepartamentoPorId(1L));

        verify(this.departamentoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerDepartamento() deve chamar metodo delete()")
    void removerDepartamentoDeveChamarMetodoDelete() {
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.usuarioRepository.existsByDepartamentoId(1L)).thenReturn(false);

        //Act:
        this.departamentoService.removerDepartamentoPorId(1L);

        //Assert:
        verify(this.departamentoRepository, times(1)).delete(departamento);
    }



    /**<p><b>listarUsuariosDoDepartamento():</b></p>
     *
     *  <p>1-Deve lançar DepartamentoNaoEncontradoException</p>
     *  <p>2-Deve retornar uma lista de UsuarioResumoResponseDTO</p>
     *  <p>2-Deve retornar uma lista vazia</p>
     */
    @Test
    @DisplayName("listarUsuariosDoDepartamento() deve lançar DepartamentoNaoEncotradoException")
    void listarUsuariosDoDepartamentoDeveLancarDepartamentoNaoEncontradoException() {
        //Arrange:
        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> this.departamentoService.listarUsuariosDoDepartamento(1L));

        verify(this.usuarioMapper, never()).toResumoResponseDTO(any());
    }


    @Test
    @DisplayName("listarUsuariosDoDepartamento() deve retornar Lista de UsuarioResumoResponseDTO")
    void listarUsuariosDoDepartamentoDeveRetornarListaDeUsuarioResumoResponseDTO() {
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();
        Usuario usuario = new UsuarioBuilder().comDepartamento(departamento).build();
        UsuarioResumoResponseDTO resumoResponseDTO = new UsuarioResumoResponseDTOBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.usuarioRepository.findByDepartamentoId(1L)).thenReturn(List.of(usuario));
        when(this.usuarioMapper.toResumoResponseDTO(usuario)).thenReturn(resumoResponseDTO);

        //Act:
        List<UsuarioResumoResponseDTO> retorno = this.departamentoService.listarUsuariosDoDepartamento(1L);

        //Assert:
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertNotNull(retorno);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Jorge da Silva", retorno.get(0).getNome()),
                () -> Assertions.assertEquals("7001", retorno.get(0).getMatricula())
        );

        verify(this.usuarioMapper, times(1)).toResumoResponseDTO(usuario);
    }

    @Test
    @DisplayName("listarTodosDepartamento() deve retornar uma lista vazia")
    void listarUsuariosDoDepartamentoDeveRetornarListaVazia (){
        //Arrange:
        Departamento departamento = new DepartamentoBuilder().build();

        when(this.departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(this.usuarioRepository.findByDepartamentoId(1L)).thenReturn(List.of());

        //Act:
        List<UsuarioResumoResponseDTO> retorno = this.departamentoService.listarUsuariosDoDepartamento(1L);

        //Assert:
        Assertions.assertTrue(retorno.isEmpty());
        Assertions.assertNotNull(retorno);
    }
}