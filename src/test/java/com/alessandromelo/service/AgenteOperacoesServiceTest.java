package com.alessandromelo.service;


import com.alessandromelo.builders.agente.AgenteBuilder;
import com.alessandromelo.builders.agenteoperacoes.*;
import com.alessandromelo.builders.comando.ComandoBuilder;
import com.alessandromelo.builders.metricasdispositivo.MetricasDispositivoBuilder;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.atualizarstatus.AtualizarStatusResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.entity.MetricasDispositivo;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.enums.ComandoStatus;
import com.alessandromelo.enums.ComandoTipo;
import com.alessandromelo.enums.MetricasTipo;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.mapper.agenteoperacoes.AtualizarStatusMapper;
import com.alessandromelo.mapper.agenteoperacoes.BuscarComandosPendentesMapper;
import com.alessandromelo.mapper.agenteoperacoes.EnviarMetricasMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.ComandoRepository;
import com.alessandromelo.repository.MetricasDispositivoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
class AgenteOperacoesServiceTest {


    private AgenteOperacoesService agenteOperacoesService;

    @Mock
    private AgenteRepository agenteRepository;
    @Mock
    private AtualizarStatusMapper atualizarStatusMapper;
    @Mock
    private ComandoRepository comandoRepository;
    @Mock
    private BuscarComandosPendentesMapper buscarComandosPendentesMapper;
    @Mock
    private MetricasDispositivoRepository metricasDispositivoRepository;
    @Mock
    private EnviarMetricasMapper enviarMetricasMapper;

    @Captor
    private ArgumentCaptor<Agente> agenteCaptor;
    @Captor
    private ArgumentCaptor<List<Comando>> listaComandoCaptor;
    @Captor
    private ArgumentCaptor<MetricasDispositivo> metricasDispositivoCaptor;


    private final LocalDateTime DATA_FIXA = LocalDateTime.of(2025, 3, 5, 22, 10, 7);

    private final Clock clockFixo = Clock.fixed(DATA_FIXA.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    @BeforeEach
    void setUp(){
        this.agenteOperacoesService = new AgenteOperacoesService(
                this.agenteRepository,
                this.atualizarStatusMapper,
                this.comandoRepository,
                this.buscarComandosPendentesMapper,
                this.metricasDispositivoRepository,
                this.enviarMetricasMapper,
                this.clockFixo);
    }




    /**<p><b>atualizarStatus():</b></p>
     *
     *  <p>1 -Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2 -Deve retornar um AtualizarStatusResponseDTO </p>
     */
    @Test
    @DisplayName("atualizarStatus() deve lançar AgenteNaoEncontradoException")
    void atualizarStatusDeveLancarAgenteNaoEncontradoException() {
        //Arrange:
        AtualizarStatusRequestDTO requestDTO = new AtualizarStatusRequestDTOBuilder()
                .comVersao("BETA V2.2.2").build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteOperacoesService.atualizarStatus(1L, requestDTO));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarStatus() deve retornar AtualizarStatusResponseDTO")
    void atualizarStatusDeveRetornarAtualizarStatusResponseDTO() {
        //Arrange:
        AtualizarStatusRequestDTO requestDTO = new AtualizarStatusRequestDTOBuilder()
                .comVersao("BETA V2.2.2").build();
        Agente agente = new AgenteBuilder().build();
        AtualizarStatusResponseDTO responseDTO = new AtualizarStatusResponseDTOBuilder()
                .comVersao("BETA V2.2.2")
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.atualizarStatusMapper.toResponseDTO(agente)).thenReturn(responseDTO);

        //Act:
        AtualizarStatusResponseDTO retorno = this.agenteOperacoesService.atualizarStatus(1L, requestDTO);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", retorno.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }



    /**<p><b>buscarComandosPendentes():</b></p>
     *
     *  <p>1 -Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2 -Deve retornar um BuscarComandosPendentesResponseDTO com uma lista de Comandos </p>
     *  <p>3 -Deve retornar um BuscarComandosPendentesResponseDTO com lista vazia</p>
     */
    @Test
    @DisplayName("buscarComandosPendentes() deve lançar AgenteNaoEncontradoException")
    void buscarComandosPendentesDeveLancarAgenteNaoEncontradoException() {
        //Arrange:
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteOperacoesService.buscarComandosPendentes(1L));

        verify(this.agenteRepository, never()).save(any());
    }


    @Test
    @DisplayName("buscarComandosPendentes() deve retornar BuscarComandosPendentesResponseDTO com uma lista de Comandos")
    void buscarComandosPendentesDeveRetornarBuscarComandosPendentesResponseDTOComListaDeComandos() {
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        Comando comando = new ComandoBuilder().build();
        List<Comando> comandos = List.of(comando);
        BuscarComandosPendentesResponseDTO responseDTO = new BuscarComandosPendentesResponseDTOBuilder().build();


        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.comandoRepository.findByAgenteIdAndStatusOrderByDataCriacaoAsc(1L, ComandoStatus.PENDENTE))
                .thenReturn(comandos);
        when(this.buscarComandosPendentesMapper.toResponseDTO(1L, comandos)).thenReturn(responseDTO);

        //Act:
        BuscarComandosPendentesResponseDTO retorno = this.agenteOperacoesService.buscarComandosPendentes(1L);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.buscarComandosPendentesMapper, times(1))
                .toResponseDTO(eq(1L), this.listaComandoCaptor.capture());
        List<Comando> listaCapturada = this.listaComandoCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getAgenteId()),

                () -> Assertions.assertEquals(1L, listaCapturada.get(0).getId()),
                () -> Assertions.assertEquals(ComandoTipo.BLOQUEAR, listaCapturada.get(0).getTipo()),
                () -> Assertions.assertEquals(ComandoStatus.PENDENTE, listaCapturada.get(0).getStatus()),
                () -> Assertions.assertEquals(LocalDateTime.of(2026, 3, 10, 10, 22),
                        listaCapturada.get(0).getDataCriacao())
        );
        Assertions.assertEquals(1, listaCapturada.size());

    }

    @Test
    @DisplayName("buscarComandosPendentes() deve retornar BuscarComandosPendentesResponseDTO com uma lista vazia")
    void buscarComandosPendentesDeveRetornarBuscarComandosPendentesResponseDTOComListaVazia() {
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        List<Comando> comandos = List.of();
        BuscarComandosPendentesResponseDTO responseDTO = new BuscarComandosPendentesResponseDTOBuilder().build();


        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.comandoRepository.findByAgenteIdAndStatusOrderByDataCriacaoAsc(1L, ComandoStatus.PENDENTE))
                .thenReturn(comandos);
        when(this.buscarComandosPendentesMapper.toResponseDTO(1L, comandos)).thenReturn(responseDTO);

        //Act:
        BuscarComandosPendentesResponseDTO retorno = this.agenteOperacoesService.buscarComandosPendentes(1L);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.buscarComandosPendentesMapper, times(1))
                .toResponseDTO(eq(1L), this.listaComandoCaptor.capture());
        List<Comando> listaCapturada = this.listaComandoCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getAgenteId()),

                () -> Assertions.assertTrue(listaCapturada.isEmpty())
        );
    }



    /**<p><b>enviarMetricas():</b></p>
     *
     *  <p>1 -Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2 -Deve retornar um EnviarMetricasResponseDTO </p>
     */
    @Test
    @DisplayName("enviarMetricas() deve lançar AgenteNaoEncontradoException")
    void enviarMetricasDeveLancarAgenteNaoEncontradoException() {
        //Arrange:
        EnviarMetricasRequestDTO requestDTO = new EnviarMetricasRequestDTOBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteOperacoesService.enviarMetricas(1L,requestDTO));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("enviarMetricas() deve retornar um EnviarMetricasResponseDTO")
    void enviarMetricasDeveRetornarEnviarMetricasResponseDTO() {
        //Arrange:
        EnviarMetricasRequestDTO requestDTO = new EnviarMetricasRequestDTOBuilder().build();
        Agente agente = new AgenteBuilder().build();
        MetricasDispositivo metricasDispositivo = new MetricasDispositivoBuilder().build();
        EnviarMetricasResponseDTO responseDTO = new EnviarMetricasResponseDTOBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.enviarMetricasMapper.toEntity(requestDTO)).thenReturn(metricasDispositivo);
        when(this.metricasDispositivoRepository.save(metricasDispositivo)).thenReturn(metricasDispositivo);
        when(this.enviarMetricasMapper.toResponse(metricasDispositivo)).thenReturn(responseDTO);

        //Act:
        EnviarMetricasResponseDTO retorno = this.agenteOperacoesService.enviarMetricas(1L, requestDTO);

        //Assert:
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente agenteCapturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(DATA_FIXA, agenteCapturado.getDataUltimaAtividade())
        );

        verify(this.metricasDispositivoRepository).save(this.metricasDispositivoCaptor.capture());
        MetricasDispositivo metricasDispositivoCapturado = this.metricasDispositivoCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(MetricasTipo.BATERIA, metricasDispositivoCapturado.getTipo()),
                () -> Assertions.assertEquals("50", metricasDispositivoCapturado.getValor()),
                () -> Assertions.assertEquals("%", metricasDispositivoCapturado.getUnidade()),
                () -> Assertions.assertEquals(LocalDateTime.of(2026, 3, 11, 8, 25), metricasDispositivoCapturado.getDataColeta()),

                () -> Assertions.assertEquals(1L ,metricasDispositivoCapturado.getAgente().getId() )
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(MetricasTipo.BATERIA, retorno.getTipo()),
                () -> Assertions.assertEquals("50", retorno.getValor()),
                () -> Assertions.assertEquals("%", retorno.getUnidade()),
                () -> Assertions.assertEquals(LocalDateTime.of(2026, 3, 11, 8, 25), retorno.getDataColeta())
        );

    }
}