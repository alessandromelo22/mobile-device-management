package com.alessandromelo.service;

import com.alessandromelo.builders.agente.AgenteBuilder;
import com.alessandromelo.builders.comando.ComandoBuilder;
import com.alessandromelo.builders.comando.ComandoRequestDTOBuilder;
import com.alessandromelo.builders.comando.ComandoResponseDTOBuilder;
import com.alessandromelo.dto.comando.ComandoRequestDTO;
import com.alessandromelo.dto.comando.ComandoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.enums.ComandoTipo;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.exception.comando.ComandoNaoEncontradoException;
import com.alessandromelo.mapper.ComandoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.ComandoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComandoServiceTest {

    @InjectMocks
    private ComandoService comandoService;

    @Mock
    private ComandoRepository comandoRepository;
    @Mock
    private ComandoMapper comandoMapper;
    @Mock
    private AgenteRepository agenteRepository;

    @Captor
    private ArgumentCaptor<Comando> comandoCaptor;


    private final LocalDateTime DATA_FIXA = LocalDateTime.of(2025, 3, 5, 22, 10, 7);

    private final Clock clockFixo = Clock.fixed(DATA_FIXA.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    @BeforeEach
    void setUp(){

        this.comandoService = new ComandoService(this.comandoRepository,this.comandoMapper,this.agenteRepository,this.clockFixo);
    }


    /**<p><b>buscarTodosComandosVinculadosAoAgentes():</b></p>
     *
     *  <p>1- Deve lançar AgenteNaoEncontradoException</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     *  <p>3- Deve retornar uma lista de ComandoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarTodosComandosVinculadosAoAgente() deve lançar AgenteNaoEncontradoException")
    void buscarTodosComandosVinculadosAoAgenteDeveLancarAgenteNaoEncontradoException(){
        //Arrange:
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.comandoService.buscarTodosComandosVinculadosAoAgente(1L));

        verify(this.comandoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarTodosComandosVinculadosAoAgente() deve retornar lista vazia")
    void buscarTodosComandosVinculadosAoAgenteDeveRetornarListaVazia(){
        //Arrange:
        Agente agente = new AgenteBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.comandoRepository.findByAgenteId(1L)).thenReturn(List.of());

        //Act:
        List<ComandoResponseDTO> retorno = this.comandoService.buscarTodosComandosVinculadosAoAgente(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("buscarTodosComandosVinculadosAoAgente() deve retornar lista de ComandosResponseDTO")
    void buscarTodosComandosVinculadosAoAgenteDeveRetornarListaDeComandoResponseDTO(){
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        Comando comando = new ComandoBuilder().build();
        ComandoResponseDTO responseDTO = new ComandoResponseDTOBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.comandoRepository.findByAgenteId(1L)).thenReturn(List.of(comando));
        when(this.comandoMapper.toResponseDTO(comando)).thenReturn(responseDTO);

        //Act:
        List<ComandoResponseDTO> retorno = this.comandoService.buscarTodosComandosVinculadosAoAgente(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, retorno.get(0).getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, retorno.get(0).getStatus())
        );

        verify(this.comandoMapper,times(1)).toResponseDTO(comando);
    }



    /**<p><b>buscarComandosPorStatus():</b></p>
     *
     *  <p>1- Deve retornar uma lista vazia</p>
     *  <p>2- Deve retornar uma lista de ComandoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarComandosPorStatus() deve retornar lista vazia")
    void buscarComandosPorStatusDeveRetornarListaVazia(){
        //Arrange:

        when(this.comandoRepository.findByStatus(ComandoStatus.PENDENTE)).thenReturn(List.of());

        //Act:
        List<ComandoResponseDTO> retorno = this.comandoService.buscarComandosPorStatus(ComandoStatus.PENDENTE);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("buscarComandosPorStatus() deve retornar lista de ComandosResponseDTO")
    void buscarComandosPorStatusDeveRetornarListaDeComandoResponseDTO(){
        //Arrange:
        Comando comando = new ComandoBuilder().build();
        ComandoResponseDTO responseDTO = new ComandoResponseDTOBuilder().build();

        when(this.comandoRepository.findByStatus(ComandoStatus.PENDENTE)).thenReturn(List.of(comando));
        when(this.comandoMapper.toResponseDTO(comando)).thenReturn(responseDTO);

        //Act:
        List<ComandoResponseDTO> retorno = this.comandoService.buscarComandosPorStatus(ComandoStatus.PENDENTE);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, retorno.get(0).getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, retorno.get(0).getStatus())
        );

        verify(this.comandoMapper,times(1)).toResponseDTO(comando);
    }



    /**<p><b>buscarComandosPorId():</b></p>
     *
     *  <p>1- Deve lançar ComandoNaoEncontradoException</p>
     *  <p>2- Deve retornar um ComandoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarComandosPorId() deve lançar ComandoNaoEncontradoException")
    void buscarComandosPorIdDeveLancarComandoNaoEncontradoException(){
        //Arrange:
        when(this.comandoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(ComandoNaoEncontradoException.class,
                () -> this.comandoService.buscarComandoPorId(1L));

        verify(this.comandoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarComandosPorId() deve retornar ComandoResponseDTO")
    void buscarComandosPorIdDeveRetornarComandoResponseDTO(){
        //Arrange:
        Comando comando = new ComandoBuilder().build();
        ComandoResponseDTO responseDTO = new ComandoResponseDTOBuilder().build();

        when(this.comandoRepository.findById(1L)).thenReturn(Optional.of(comando));
        when(this.comandoMapper.toResponseDTO(comando)).thenReturn(responseDTO);

        //Act:
        ComandoResponseDTO retorno = this.comandoService.buscarComandoPorId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, retorno.getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, retorno.getStatus())
        );
    }



    /**<p><b>criarComando():</b></p>
     *
     *  <p>1- Deve lançar AgenteNaoEncontrado</p>
     *  <p>2- Deve retornar um ComandoResponseDTO</p>
     */
    @Test
    @DisplayName("criarComando() deve lançar AgenteNaoEncontrado")
    void criarComandoDeveLancarAgenteNaoEncontrado(){
        //Arrange:
        ComandoRequestDTO requestDTO = new ComandoRequestDTOBuilder()
                .comAgenteId(1L)
                .build();
        Comando comando = new ComandoBuilder().build();

        when(this.comandoMapper.toEntity(requestDTO)).thenReturn(comando);
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.comandoService.criarComando(requestDTO));

        verify(this.comandoMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("criarComando() deve retornar ComandoResponseDTO")
    void criarComandoDeveRetornarComandoResponseDTO(){
        //Arrange:
        ComandoRequestDTO requestDTO = new ComandoRequestDTOBuilder()
                .comAgenteId(1L)
                .build();
        Comando comando = new ComandoBuilder().build();
        Agente agente = new AgenteBuilder().build();
        ComandoResponseDTO responseDTO = new ComandoResponseDTOBuilder().build();

        when(this.comandoMapper.toEntity(requestDTO)).thenReturn(comando);
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.comandoRepository.save(comando)).thenReturn(comando);
        when(this.comandoMapper.toResponseDTO(comando)).thenReturn(responseDTO);

        //Act:
        ComandoResponseDTO retorno = this.comandoService.criarComando(requestDTO);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.comandoRepository).save(this.comandoCaptor.capture());
        Comando capturado = this.comandoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, capturado.getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, capturado.getStatus()),
                () -> Assertions.assertEquals("", capturado.getParametros()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataCriacao()),

                () -> Assertions.assertEquals(1L, capturado.getAgente().getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", capturado.getAgente().getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getAgente().getStatus())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, retorno.getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, retorno.getStatus())
        );
    }


}