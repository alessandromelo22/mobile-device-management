package com.alessandromelo.service;

import com.alessandromelo.builders.dispositivo.DispositivoBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoRequestDTOBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoResponseDTOBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoUsuarioResponseDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioBuilder;
import com.alessandromelo.builders.usuario.UsuarioRequestDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioResponseDTOBuilder;
import com.alessandromelo.dto.dispositivo.DispositivoRequestDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoUsuarioResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioRequestDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.enums.DispositivoStatus;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.exception.dispositivo.NumeroDeSerieJaCadastradoException;
import com.alessandromelo.exception.global.EntidadeEmUsoException;
import com.alessandromelo.exception.usuario.EmailJaCadastradoException;
import com.alessandromelo.exception.usuario.UsuarioNaoEncontradoException;
import com.alessandromelo.mapper.DispositivoMapper;
import com.alessandromelo.repository.AgenteRepository;
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
class DispositivoServiceTest {

    @InjectMocks
    private DispositivoService dispositivoService;

    @Mock
    private DispositivoRepository dispositivoRepository;
    @Mock
    private DispositivoMapper dispositivoMapper;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AgenteRepository agenteRepository;

    @Captor
    private ArgumentCaptor<Dispositivo> dispositivoCaptor;



    //Pensar se compensa adicionar testes vizando verificar se
    // o retorno possui o objeto de relacionamento correto,
    // no caso está sendo testado apenas o retorno sem os objetos de relacionamento





    /**<p><b>listarTodosDispositivos():</b></p>
     *
     *  <p>1- Deve retornar uma lista vazia</p>
     *  <p>2- Deve retornar uma lista de DispositivoResponseDTO</p>
     */
    @Test
    @DisplayName("listarTodosDispositivos() deve retornar uma lista vazia")
    void listarTodosDispositivosDeveRetornarListaVazia() {
        //Arrange:
        when(this.dispositivoRepository.findAll()).thenReturn(List.of());

        //Act:
        List<DispositivoResponseDTO> retorno = this.dispositivoService.listarTodosDispositivos();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("listarTodosDispositivos() deve retornar uma lista de DispositivoResponseDTO")
    void listarTodosDispositivosDeveRetornarListaDeDispositivoResponseDTO() {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder().build();

        when(this.dispositivoRepository.findAll()).thenReturn(List.of(dispositivo));
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);

        //Act:
        List<DispositivoResponseDTO> retorno = this.dispositivoService.listarTodosDispositivos();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Aspire 5",retorno.get(0).getModelo()),
                () -> Assertions.assertEquals("Acer",retorno.get(0).getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",retorno.get(0).getNumeroSerie())
        );
        verify(this.dispositivoMapper, times(1)).toResponseDTO(dispositivo);
    }



    /**<p><b>buscarDispositivoPorId():</b></p>
     *
     *  <p>1- Deve lançar DispositivoNaoEncontradoException</p>
     *  <p>2- Deve retornar um DispositivoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarDispositivoPorId() deve lançar DispositivoNaoEncontradoException")
    void buscarDispositivoPorIdDeveLancarDispositivoNaoEncontradoException (){
        //Arrange:
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> this.dispositivoService.buscarDispositivoPorId(1L));

        verify(this.dispositivoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarDispositivoPorId() deve retornar DispositivoResponseDTO")
    void buscarDispositivoPorIdDeveRetornarDispositivoResponseDTO(){
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);

        //Act:
        DispositivoResponseDTO retorno = this.dispositivoService.buscarDispositivoPorId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("Aspire 5",retorno.getModelo()),
                () -> Assertions.assertEquals("Acer",retorno.getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",retorno.getNumeroSerie())
        );
    }



    /**<p><b>buscarDispositivosPorStatus():</b></p>
     *
     *  <p>1- Deve retornar lista vazia</p>
     *  <p>2- Deve retornar uma lista de DispositivoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarDispositivosPorStatus() deve retornar lista vazia")
    void buscarDispositivoPorStatusDeveRetornarListaVazia (){
        //Arrange:
        when(this.dispositivoRepository.findByStatus(DispositivoStatus.ATIVO)).thenReturn(List.of());

        //Act:
        List<DispositivoResponseDTO> retorno = this.dispositivoService.buscarDispositivosPorStatus(DispositivoStatus.ATIVO);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("buscarDispositivosPorStatus() deve retornar uma lista de DispositivoResponseDTO")
    void buscarDispositivosPorStatusDeveRetornarListaDeDispositivoResponseDTO() {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder().build();

        when(this.dispositivoRepository.findByStatus(DispositivoStatus.ATIVO)).thenReturn(List.of(dispositivo));
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);

        //Act:
        List<DispositivoResponseDTO> retorno = this.dispositivoService.buscarDispositivosPorStatus(DispositivoStatus.ATIVO);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("Aspire 5",retorno.get(0).getModelo()),
                () -> Assertions.assertEquals("Acer",retorno.get(0).getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",retorno.get(0).getNumeroSerie())
        );
        verify(this.dispositivoMapper, times(1)).toResponseDTO(dispositivo);
    }



    /**<p><b>cadastrarNovoDispositivo():</b></p>
     *
     *  <p>1-Deve lançar NumeroDeSerieJaCadastradoException </p>
     *  <p>2-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>3-Deve retornar DispositivoResponseDTO </p>
     *  <p>4-Deve retornar DispositivoResponseDTO sem o objeto Usuario</p>
     */
    @Test
    @DisplayName("cadastrarNovoDispositivo() deve lançar NumeroDeSerieJaCadastradoException")
    void cadastrarNovoDispositivoDeveLancarNumeroDeSerieJaCadastradoException(){
        //Arrange
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder().build();

        when(this.dispositivoRepository.existsByNumeroSerie(requestDTO.getNumeroSerie())).thenReturn(true);

        //Act
        //Asert
        Assertions.assertThrows(NumeroDeSerieJaCadastradoException.class,
                () -> dispositivoService.cadastrarNovoDispositivo(requestDTO));

        verify(this.dispositivoRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoDispositivo() deve lançar UsuarioNaoEncontradoException")
    void cadastrarNovoDispositivoDeveLancarUsuarioNaoEncontradoException(){
        //Arrange
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder().comUsuarioId(1L).build();
        Dispositivo dispositivo = new DispositivoBuilder().build();

        when(this.dispositivoRepository.existsByNumeroSerie(requestDTO.getNumeroSerie())).thenReturn(false);
        when(this.dispositivoMapper.toEntity(requestDTO)).thenReturn(dispositivo);
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Asert
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> dispositivoService.cadastrarNovoDispositivo(requestDTO));

        verify(this.dispositivoRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoDispositivo() deve retornar DispositivoResponseDTO")
    void cadastrarNovoDispositivoDeveRetornarDispositivoResponseDTO(){
        //Arrange
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder().comStatus(null)
                .comUsuarioId(1L).build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        Usuario usuario = new UsuarioBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder().build();


        when(this.dispositivoRepository.existsByNumeroSerie(requestDTO.getNumeroSerie())).thenReturn(false);
        when(this.dispositivoMapper.toEntity(requestDTO)).thenReturn(dispositivo);
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);

        //Act
        DispositivoResponseDTO retorno = this.dispositivoService.cadastrarNovoDispositivo(requestDTO);

        //Asert
        Assertions.assertNotNull(retorno);

        verify(this.dispositivoRepository).save(this.dispositivoCaptor.capture());
        Dispositivo capturado = this.dispositivoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals("Aspire 5", capturado.getModelo()),
                () -> Assertions.assertEquals("Acer",capturado.getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",capturado.getNumeroSerie()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO,capturado.getStatus()),

                () -> Assertions.assertEquals(1L,capturado.getUsuario().getId()),
                () -> Assertions.assertEquals("Jorge da Silva",capturado.getUsuario().getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com",capturado.getUsuario().getEmail()),
                () -> Assertions.assertEquals("7001",capturado.getUsuario().getMatricula())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals("Aspire 5", retorno.getModelo()),
                () -> Assertions.assertEquals("Acer",retorno.getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",retorno.getNumeroSerie()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO,retorno.getStatus())
        );

    }


    @Test
    @DisplayName("cadastrarNovoDispositivo() deve retornar DispositivoResponseDTO sem objeto Usuario")
    void cadastrarNovoDispositivoDeveRetornarDispositivoResponseDTOSemObjetoUsuario(){
        //Arrange
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder().comStatus(null).build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder().build();


        when(this.dispositivoRepository.existsByNumeroSerie(requestDTO.getNumeroSerie())).thenReturn(false);
        when(this.dispositivoMapper.toEntity(requestDTO)).thenReturn(dispositivo);
        when(this.dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);

        //Act
        DispositivoResponseDTO retorno = this.dispositivoService.cadastrarNovoDispositivo(requestDTO);

        //Asert
        Assertions.assertNotNull(retorno);

        verify(this.dispositivoRepository).save(this.dispositivoCaptor.capture());
        Dispositivo capturado = this.dispositivoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals("Aspire 5", capturado.getModelo()),
                () -> Assertions.assertEquals("Acer",capturado.getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",capturado.getNumeroSerie()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO,capturado.getStatus()),

                () -> Assertions.assertNull(capturado.getUsuario())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals("Aspire 5", retorno.getModelo()),
                () -> Assertions.assertEquals("Acer",retorno.getMarca()),
                () -> Assertions.assertEquals("6977a67dey0",retorno.getNumeroSerie()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO,retorno.getStatus())
        );
    }



    /**<p><b>atualizarDispositivo():</b></p>
     *
     *  <p>1-Deve lançar DispositivoNaoEncontradoException </p>
     *  <p>2-Deve lançar NumeroDeSerieJaCadastradoException </p>
     *  <p>3-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>5-Deve retornar DispositivoResponseDTO </p>
     *  <p>6-Deve retornar DispositivoResponseDTO sem o objeto Usuario</p>
     *
     */
    @Test
    @DisplayName("atualizarDispositivo() deve lançar DispositivoNaoEncontradoException")
    void atualizarDispositivoDeveLancarDispositivoNaoEncontradoException() {
        //Arrange:
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t").build();


        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> this.dispositivoService.atualizarDispositivo(1L,requestDTO));

        verify(this.dispositivoRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarDispositivo() deve lançar NumeroDeSerieJaCadastradoException")
    void atualizarDispositivoDeveLancarNumeroDeSerieJaCadastradoException() {
        //Arrange:
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t").build();

        Dispositivo dispositivo = new DispositivoBuilder().build();


        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.dispositivoRepository.existsByNumeroSerieAndIdNot(requestDTO.getNumeroSerie(), 1L))
                .thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(NumeroDeSerieJaCadastradoException.class,
                () -> this.dispositivoService.atualizarDispositivo(1L,requestDTO));

        verify(this.dispositivoRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarDispositivo() deve lançar UsuarioNaoEncontradoException")
    void atualizarDispositivoDeveLancarUsuarioNaoEncontradoException() {
        //Arrange:
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t")
                .comUsuarioId(1L).build();

        Dispositivo dispositivo = new DispositivoBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.dispositivoRepository.existsByNumeroSerieAndIdNot(requestDTO.getNumeroSerie(), 1L))
                .thenReturn(false);
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());


        //Act:
        //Assert:
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> this.dispositivoService.atualizarDispositivo(1L,requestDTO));

        verify(this.dispositivoRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarDispositivo() deve retornar DispositivoResponseDTO")
    void atualizarDispositivoDeveRetornarDispositivoResponseDTO() {
        //Arrange:
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t")
                .comUsuarioId(1L).build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        Usuario usuario = new UsuarioBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t").build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.dispositivoRepository.existsByNumeroSerieAndIdNot(requestDTO.getNumeroSerie(), 1L))
                .thenReturn(false);
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);


        //Act:
        DispositivoResponseDTO retorno = this.dispositivoService.atualizarDispositivo(1L, requestDTO);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.dispositivoRepository).save(this.dispositivoCaptor.capture());
        Dispositivo capturado = this.dispositivoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("MacBook Air", capturado.getModelo()),
                () -> Assertions.assertEquals("Apple", capturado.getMarca()),
                () -> Assertions.assertEquals("d35gr4562t", capturado.getNumeroSerie()),

                () -> Assertions.assertEquals(1L, capturado.getUsuario().getId()),
                () -> Assertions.assertEquals("Jorge da Silva", capturado.getUsuario().getNome()),
                () -> Assertions.assertEquals("jorginds69@gmail.com", capturado.getUsuario().getEmail()),
                () -> Assertions.assertEquals("7001", capturado.getUsuario().getMatricula())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("MacBook Air", retorno.getModelo()),
                () -> Assertions.assertEquals("Apple", retorno.getMarca()),
                () -> Assertions.assertEquals("d35gr4562t", retorno.getNumeroSerie())
        );
    }

    @Test
    @DisplayName("atualizarDispositivo() deve retornar DispositivoResponseDTO sem o objeto Usuario")
    void atualizarDispositivoDeveRetornarDispositivoResponseDTOSemObjetoUsuario() {
        //Arrange:
        DispositivoRequestDTO requestDTO = new DispositivoRequestDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t").build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        DispositivoResponseDTO responseDTO = new DispositivoResponseDTOBuilder()
                .comModelo("MacBook Air")
                .comMarca("Apple")
                .comNumeroSerie("d35gr4562t").build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.dispositivoRepository.existsByNumeroSerieAndIdNot(requestDTO.getNumeroSerie(), 1L))
                .thenReturn(false);
        when(this.dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(this.dispositivoMapper.toResponseDTO(dispositivo)).thenReturn(responseDTO);


        //Act:
        DispositivoResponseDTO retorno = this.dispositivoService.atualizarDispositivo(1L, requestDTO);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.dispositivoRepository).save(this.dispositivoCaptor.capture());
        Dispositivo capturado = this.dispositivoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("MacBook Air", capturado.getModelo()),
                () -> Assertions.assertEquals("Apple", capturado.getMarca()),
                () -> Assertions.assertEquals("d35gr4562t", capturado.getNumeroSerie()),

                () -> Assertions.assertNull(capturado.getUsuario())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("MacBook Air", retorno.getModelo()),
                () -> Assertions.assertEquals("Apple", retorno.getMarca()),
                () -> Assertions.assertEquals("d35gr4562t", retorno.getNumeroSerie())
        );
    }



    /**<p><b>removerDispositivoPorId():</b></p>
     *
     *  <p>1-Deve lançar DispositivoNaoEncontradoException </p>
     *  <p>2-Deve lançar EntidadeEmUsoException </p>
     *  <p>3-Deve chamar metodo delete() </p>
     */
    @Test
    @DisplayName("removerDispositivoPorId() deve lançar DispositivoNaoEncontradoException")
    void removerDispositivoPorIdDeveLancarDispositivoNaoEncontradoException() {
        //Arrange:
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> this.dispositivoService.removerDispositivoPorId(1L));

        verify(this.dispositivoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerDispositivoPorId() deve lançar EntidadeEmUsoException")
    void removerDispositivoPorIdDeveLancarEntidadeEmUsoException() {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.agenteRepository.existsByDispositivoId(1L)).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(EntidadeEmUsoException.class,
                () -> this.dispositivoService.removerDispositivoPorId(1L));

        verify(this.dispositivoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("removerDispositivoPorId() deve chamar metodo delete()")
    void removerDispositivoPorIdDeveChamarMetodoDelete() {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.agenteRepository.existsByDispositivoId(1L)).thenReturn(false);

        //Act:
        this.dispositivoService.removerDispositivoPorId(1L);

        //Assert:
        verify(this.dispositivoRepository, times(1)).delete(dispositivo);
    }
    


    /**<p><b>vincularDispositivoAoUsuario():</b></p>
     *
     *  <p>1-Deve lançar DispositivoNaoEncontradoException </p>
     *  <p>2-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>3-Deve retornar um DispositivoUsuarioResponseDTO </p>
     */
    @Test
    @DisplayName("vincularDispositivoAoUsuario deve lançar DispositivoNaoEncontradoException")
    void vincularDispositivoAoUsuarioDeveLancarDispositivoNaoEncontradoException () {
        //Arrange:
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> this.dispositivoService.vincularDispositivoAoUsuario(1L, 1L));

        verify(this.dispositivoRepository, never()).save(any());
    }

    @Test
    @DisplayName("vincularDispositivoAoUsuario deve lançar UsuarioNaoEncontradoException")
    void vincularDispositivoAoUsuarioDeveLancarUsuarioNaoEncontradoException () {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> this.dispositivoService.vincularDispositivoAoUsuario(1L, 1L));

        verify(this.dispositivoRepository, never()).save(any());
    }

    @Test
    @DisplayName("vincularDispositivoAoUsuario deve retornar um DispositivoUsuarioResponseDTO")
    void vincularDispositivoAoUsuarioDeveRetornaDispositivoUsuarioResponseDTO () {
        //Arrange:
        Dispositivo dispositivo = new DispositivoBuilder().build();
        Usuario usuario = new UsuarioBuilder().build();

        DispositivoUsuarioResponseDTO responseDTO = new DispositivoUsuarioResponseDTOBuilder().build();

        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(this.dispositivoRepository.save(dispositivo)).thenReturn(dispositivo);
        when(this.dispositivoMapper.toDispositivoUsuarioResponseDTO(dispositivo, usuario)).thenReturn(responseDTO);

        //Act:
        DispositivoUsuarioResponseDTO retorno = this.dispositivoService.vincularDispositivoAoUsuario(1L, 1L);
        
        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.dispositivoRepository).save(this.dispositivoCaptor.capture());
        Dispositivo capturado = this.dispositivoCaptor.getValue();
        
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("Aspire 5", capturado.getModelo()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO, capturado.getStatus()),
                
                () -> Assertions.assertEquals(1L, capturado.getUsuario().getId()),
                () -> Assertions.assertEquals("Jorge da Silva", capturado.getUsuario().getNome()),
                () -> Assertions.assertEquals("7001", capturado.getUsuario().getMatricula())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getDispositivoId()),
                () -> Assertions.assertEquals("Aspire 5", retorno.getModelo()),
                () -> Assertions.assertEquals(DispositivoStatus.ATIVO, retorno.getStatus())
        );
    }
}