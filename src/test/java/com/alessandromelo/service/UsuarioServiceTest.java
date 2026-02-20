package com.alessandromelo.service;

import com.alessandromelo.builders.departamento.DepartamentoBuilder;
import com.alessandromelo.builders.departamento.DepartamentoRequestDTOBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoResumoResponseDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioBuilder;
import com.alessandromelo.builders.usuario.UsuarioRequestDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioResponseDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioResumoResponseDTOBuilder;
import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioRequestDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.enums.DispositivoStatus;
import com.alessandromelo.exception.departamento.DepartamentoNaoEncontradoException;
import com.alessandromelo.exception.departamento.NomeJaCadastradoException;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.exception.usuario.EmailJaCadastradoException;
import com.alessandromelo.exception.usuario.MatriculaJaCadastradaException;
import com.alessandromelo.exception.usuario.UsuarioNaoEncontradoException;
import com.alessandromelo.mapper.DispositivoMapper;
import com.alessandromelo.mapper.UsuarioMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import com.alessandromelo.repository.DispositivoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    UsuarioMapper usuarioMapper;
    @Mock
    DepartamentoRepository departamentoRepository;
    @Mock
    DispositivoMapper dispositivoMapper;
    @Mock
    DispositivoRepository dispositivoRepository;

    @Captor
    ArgumentCaptor<Usuario> usuarioCaptor;



    /**<p><b>listarUsuarios():</b></p>
     *
     *  <p>1- Deve retornar uma lista de UsuarioResponseDTO</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     */
    @Test
    @DisplayName("listarUsuarios() deve retornar uma lista de UsuarioResponseDTO")
    void listarUsuariosDeveRetornarListaDeUsuarioResponseDTO() {
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTOBuilder().build();

        when(this.usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(this.usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        //Act:
        List<UsuarioResponseDTO> retorno = this.usuarioService.listarUsuarios();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Jorge da Silva",retorno.get(0).getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com",retorno.get(0).getEmail()),
                () -> Assertions.assertEquals("7001",retorno.get(0).getMatricula())
        );
        verify(this.usuarioMapper, times(1)).toResponseDTO(usuario);
    }

    @Test
    @DisplayName("listarUsuarios() deve retornar uma lista vazia")
    void listarTodosDepartamentosDeveRetornarUmaListaVazia (){
        //Arrange:
        when(this.usuarioRepository.findAll()).thenReturn(List.of());

        //Act:
        List<UsuarioResponseDTO> retorno = this.usuarioService.listarUsuarios();

        //Assert:
        Assertions.assertTrue(retorno.isEmpty());
        Assertions.assertNotNull(retorno);
    }



    /**<p><b>buscarUsuarioPorId():</b></p>
     *
     *  <p>1- Deve lançar UsuarioNaoEncontradoException</p>
     *  <p>2- Deve retornar um UsuarioResponseDTO</p>
     */
    @Test
    @DisplayName("buscarUsuarioPorId() deve lançar UsuarioNaoEncontradoException")
    void buscarUsuarioPorIdDeveLancarUsuarioNaoEncontradoException (){
        //Arrange:
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> this.usuarioService.buscarUsuarioPorId(1L));

        verify(this.usuarioMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarUsuarioPorId() deve retornar UsuarioResponseDTO")
    void buscarUsuarioPorIdDeveRetornarUsuarioResponseDTO(){
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTOBuilder().build();

        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        //Act:
        UsuarioResponseDTO retorno = this.usuarioService.buscarUsuarioPorId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Jorge da Silva", retorno.getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com", retorno.getEmail()),
                () -> Assertions.assertEquals("7001", retorno.getMatricula())
        );
    }



    /**<p><b>cadastrarNovoUsuario():</b></p>
     *
     *  <p>1-Deve lançar EmailJaCadastradoException </p>
     *  <p>2-Deve lançar MatriculaJaCadastradaException </p>
     *  <p>3-Deve lançar DepartamentoNaoEncontrado </p>
     *  <p>4-Deve retornar UsuarioResponseDTO </p>
     *
     */
    @Test
    @DisplayName("cadastrarNovoUsuario() deve lançar EmailJaCadastradoException")
    void cadastrarNovoUsuarioDeveLancarEmailJaCadastrado(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder().build();

        Mockito.when(this.usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(EmailJaCadastradoException.class,
                () -> usuarioService.cadastrarNovoUsuario(requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoUsuario() deve lançar MatriculaJaCadastradaException")
    void cadastrarNovoUsuarioDeveLancarMatriculaJaCadastrada(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder().build();

        Mockito.when(this.usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatricula(requestDTO.getMatricula())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(MatriculaJaCadastradaException.class,
                () -> usuarioService.cadastrarNovoUsuario(requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoUsuario() deve lançar DepartamentoNaoEncontradoException")
    void cadastrarNovoUsuarioDeveLancarDepartamentoNaoEncotrado(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder().comDepartamentoId(1L).build();
        Usuario usuario = new UsuarioBuilder().build();

        Mockito.when(this.usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatricula(requestDTO.getMatricula())).thenReturn(false);
        Mockito.when(this.usuarioMapper.toEntity(requestDTO)).thenReturn(usuario);
        Mockito.when(this.departamentoRepository.findById(requestDTO.getDepartamentoId()))
                .thenReturn(Optional.empty());

        //Act
        //Asert
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> this.usuarioService.cadastrarNovoUsuario(requestDTO));

        verify(this.usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoUsuario() deve retornar UsuarioResponseDTO")
    void cadastrarNovoUsuarioDeveRetornarUsuarioResponseDTO(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder().comDepartamentoId(1L).build();
        Usuario usuario = new UsuarioBuilder().build();
        Departamento departamento = new DepartamentoBuilder().build();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTOBuilder().build();


        Mockito.when(this.usuarioRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatricula(requestDTO.getMatricula())).thenReturn(false);
        Mockito.when(this.usuarioMapper.toEntity(requestDTO)).thenReturn(usuario);
        Mockito.when(this.departamentoRepository.findById(requestDTO.getDepartamentoId()))
                .thenReturn(Optional.of(departamento));

        Mockito.when(this.usuarioRepository.save(usuario)).thenReturn(usuario);
        Mockito.when(this.usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        //Act
        UsuarioResponseDTO retorno = this.usuarioService.cadastrarNovoUsuario(requestDTO);

        //Asert
        verify(this.usuarioRepository).save(this.usuarioCaptor.capture());
        Usuario capturado = this.usuarioCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("Jorge da Silva", capturado.getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com", capturado.getEmail()),
                () -> Assertions.assertEquals("7001", capturado.getMatricula())
        );

        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Jorge da Silva", retorno.getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com", retorno.getEmail()),
                () -> Assertions.assertEquals("7001", retorno.getMatricula())
        );
    }



    /**<p><b>atualizarUsuario():</b></p>
     *
     *  <p>1-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>2-Deve lançar EmailJaCadastradoException </p>
     *  <p>3-Deve lançar MatriculaJaCadastradaException </p>
     *  <p>4-Deve lançar DepartamentoNaoEncontrado </p>
     *  <p>5-Deve retornar UsuarioResponseDTO </p>
     *
     */
    @Test
    @DisplayName("atualizarUsuario() deve lançar EmailJaCadastradoException")
    void atualizarUsuarioDeveLancarUsuarioNaoEncontradoException(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("jorginds69@gmail.com")
                .comMatricula("7007").build();

        Mockito.when(this.usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        //Act
        //Asert
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.atualizarUsuario(2L,requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("atualizarUsuario() deve lançar EmailJaCadastradoException")
    void atualizarUsuarioDeveLancarEmailJaCadastradoException(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("jorginds69@gmail.com")
                .comMatricula("7007").build();
        Usuario usuario = new UsuarioBuilder().build();

        Mockito.when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(this.usuarioRepository.existsByEmailAndIdNot(requestDTO.getEmail(), 1L))
                .thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(EmailJaCadastradoException.class,
                () -> usuarioService.atualizarUsuario(1L,requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("atualizarUsuario() deve lançar MatriculaJaCadastradaException")
    void atualizarUsuarioDeveLancarMatriculaJaCadastradaException(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("henrjos77@gmail.com")
                .comMatricula("7001").build();
        Usuario usuario = new UsuarioBuilder().build();

        Mockito.when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(this.usuarioRepository.existsByEmailAndIdNot(requestDTO.getEmail(), 1L))
                .thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatriculaAndIdNot(requestDTO.getMatricula(), 1L))
                .thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(MatriculaJaCadastradaException.class,
                () -> usuarioService.atualizarUsuario(1L,requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("atualizarUsuario() deve lançar DepartamentoNaoEncontradoException")
    void atualizarUsuarioDeveLancarDepartamentoNaoEncotradoException(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("henrjos77@gmail.com")
                .comMatricula("7007")
                .comDepartamentoId(1L)
                .build();
        Usuario usuario = new UsuarioBuilder().build();

        Mockito.when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(this.usuarioRepository.existsByEmailAndIdNot(requestDTO.getEmail(), 1L))
                .thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatriculaAndIdNot(requestDTO.getMatricula(), 1L))
                .thenReturn(false);
        Mockito.when(this.departamentoRepository.findById(requestDTO.getDepartamentoId()))
                .thenReturn(Optional.empty());

        //Act
        //Asert
        Assertions.assertThrows(DepartamentoNaoEncontradoException.class,
                () -> usuarioService.atualizarUsuario(1L,requestDTO));

        Mockito.verify(this.usuarioRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("atualizarUsuario() deve retornar UsuarioResponseDTO")
    void atualizarUsuarioDeveRetornarUsuarioResponseDTO(){
        //Arrange
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("henrjos77@gmail.com")
                .comMatricula("7007")
                .comDepartamentoId(1L)
                .build();
        Usuario usuario = new UsuarioBuilder().build();
        Departamento departamento = new DepartamentoBuilder().build();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTOBuilder()
                .comNome("Henrique Jorge da Silva")
                .comEmail("henrjos77@gmail.com")
                .comMatricula("7007")
                .build();

        Mockito.when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(this.usuarioRepository.existsByEmailAndIdNot(requestDTO.getEmail(), 1L))
                .thenReturn(false);
        Mockito.when(this.usuarioRepository.existsByMatriculaAndIdNot(requestDTO.getMatricula(), 1L))
                .thenReturn(false);
        Mockito.when(this.departamentoRepository.findById(requestDTO.getDepartamentoId()))
                .thenReturn(Optional.of(departamento));
        Mockito.when(this.usuarioRepository.save(usuario)).thenReturn(usuario);
        Mockito.when(this.usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        //Act
        UsuarioResponseDTO retorno = this.usuarioService.atualizarUsuario(1L,requestDTO);

        //Asert
        verify(this.usuarioRepository).save(this.usuarioCaptor.capture());
        Usuario capturado = this.usuarioCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("Henrique Jorge da Silva", capturado.getNome()),
                () -> Assertions.assertEquals("henrjos77@gmail.com", capturado.getEmail()),
                () -> Assertions.assertEquals("7007", capturado.getMatricula())
        );

        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Henrique Jorge da Silva", retorno.getNome()),
                () -> Assertions.assertEquals("henrjos77@gmail.com", retorno.getEmail()),
                () -> Assertions.assertEquals("7007", retorno.getMatricula())
        );
    }



    /**<p><b>removerUsuario():</b></p>
     *
     *  <p>1-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>2-Deve lançar EntidadeEmUsoException </p>
     *  <p>3-Deve chamar metodo delete() </p>
     */
    @Test
    @DisplayName("removerUsuarioPorId() deve lançar UsuarioNaoEncontradoException")
    void removerUsuarioPorIdDeveLancarUsuarioNaoEncontradoException() {
        //Arrange:
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> this.usuarioService.removerUsuarioPorId(1L));

        verify(this.usuarioRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerUsuarioPorId() deve lançar EntidadeEmUsoException")
    void removerUsuarioPorIdDeveLancarEntidadeEmUsoException() {
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();

        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.existsByUsuarioId(1L)).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(EntidadeEmUsoException.class,
                () -> this.usuarioService.removerUsuarioPorId(1L));

        verify(this.usuarioRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerUsuario() deve chamar metodo delete()")
    void removerUsuarioDeveChamarMetodoDelete() {
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();

        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.existsByUsuarioId(1L)).thenReturn(false);

        //Act:
        this.usuarioService.removerUsuarioPorId(1L);

        //Assert:
        verify(this.usuarioRepository, times(1)).delete(usuario);
    }



    /**<p><b>listarDispositivosVinculadosAoUsuario():</b></p>
     *
     *  <p>1-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>2-Deve retornar lista de DispositivoResumoResponse </p>
     *  <p>3-Deve retornar lista vazia </p>
     */
    @Test
    @DisplayName("listarDispositivosVinculadosAoUsuario() deve lançar UsuarioNaoEncontradoException")
    void listarDispositivosVinculadosAoUsuarioDeveLancarUsuarioNaoEncontradoException(){
        //Arrange:
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> this.usuarioService.listarDispositivosVinculadosAoUsuario(1L));

        verify(this.dispositivoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("listarDispositivosVinculadosAoUsuario() deve retornar Lista de DispositivoResumoResponseDTO")
    void listarDispositivosVinculadosAoUsuarioDeveRetornarListaDeDispositivoResumoResponseDTO() {
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();
        Dispositivo dispositivo = new DispositivoBuilder().comUsuario(usuario).build();
        DispositivoResumoResponseDTO resumoResponseDTO = new DispositivoResumoResponseDTOBuilder().build();

        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.findByUsuarioId(1L)).thenReturn(List.of(dispositivo));
        when(this.dispositivoMapper.toResumoResponseDTO(dispositivo)).thenReturn(resumoResponseDTO);

        //Act:
        List<DispositivoResumoResponseDTO> retorno = this.usuarioService.listarDispositivosVinculadosAoUsuario(1L);

        //Assert:
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertNotNull(retorno);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Aspire 5", retorno.get(0).getModelo()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO, retorno.get(0).getStatus())
        );

        verify(this.dispositivoMapper, times(1)).toResumoResponseDTO(dispositivo);
    }

    @Test
    @DisplayName("listarDispositivosVinculadosAoUsuario() deve retornar lista vazia")
    void listarDispositivosVinculadosAoUsuarioDeveRetornarListaVazia() {
        //Arrange:
        Usuario usuario = new UsuarioBuilder().build();

        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.findByUsuarioId(1L)).thenReturn(List.of());

        //Act:
        List<DispositivoResumoResponseDTO> retorno = this.usuarioService.listarDispositivosVinculadosAoUsuario(1L);

        //Assert:
        Assertions.assertTrue(retorno.isEmpty());
        Assertions.assertNotNull(retorno);
    }



    /**<p><b>vincularDispositivoAoUsuario:</b></p>
     *
     *  <p>1-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>2-Deve retornar lista de DispositivoResumoResponse </p>
     *  <p>3-Deve retornar lista vazia </p>
     */
}