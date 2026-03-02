package com.alessandromelo.service;

import com.alessandromelo.builders.dispositivo.DispositivoBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoResponseDTOBuilder;
import com.alessandromelo.builders.usuario.UsuarioBuilder;
import com.alessandromelo.builders.usuario.UsuarioResponseDTOBuilder;
import com.alessandromelo.dto.dispositivo.DispositivoResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.entity.Usuario;
import com.alessandromelo.enums.DispositivoStatus;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.exception.usuario.UsuarioNaoEncontradoException;
import com.alessandromelo.mapper.DispositivoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.DispositivoRepository;
import com.alessandromelo.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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



    //Pensar se compensa adicionar testes vizando verificar se
    // o retorno possui o objeto de relacionamento correto,
    // no caso está sendo testado apenas o retorno sem os objetos de relacionamento





    /**<p><b>listarDispositivos():</b></p>
     *
     *  <p>1- Deve retornar uma lista de DispositivoResponseDTO</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     */
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
     *  <p>1- Deve retoranar lista vazia</p>
     *  <p>2- Deve retornar um DispositivoResponseDTO</p>
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



    /**<p><b>cadastrarNovoDispositivo():</b></p>
     *
     *  <p>1-Deve lançar NumeroDeSerieJaCadastradoException </p>
     *  <p>2-Deve lançar UsuarioNaoEncontradoException </p>
     *  <p>3-Deve retornar DispositivoResponseDTO </p>
     */
}