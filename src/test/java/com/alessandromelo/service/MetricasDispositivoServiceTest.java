package com.alessandromelo.service;

import com.alessandromelo.builders.agente.AgenteBuilder;
import com.alessandromelo.builders.metricasdispositivo.MetricasDispositivoBuilder;
import com.alessandromelo.builders.metricasdispositivo.MetricasDispositivoResponseDTOBuilder;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.metricasdispositivo.MetricasDispositivoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.MetricasDispositivo;
import com.alessandromelo.enums.MetricasTipo;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.mapper.MetricasDispositivoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.MetricasDispositivoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricasDispositivoServiceTest {

    @InjectMocks
    private MetricasDispositivoService metricasDispositivoService;

    @Mock
    private MetricasDispositivoRepository metricasDispositivoRepository;
    @Mock
    private AgenteRepository agenteRepository;
    @Mock
    private MetricasDispositivoMapper metricasDispositivoMapper;


    /**<p><b>buscarMetricasVinculadasAoAgente():</b></p>
     *
     *  <p>1- Deve lançar AgenteNaoEncontradoException</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     *  <p>3- Deve retornar uma lista de MetricasDispositivoResponseDTO</p>
     */
    @Test
    @DisplayName("buscarMetricasVinculadasAoAgente() deve lançar AgenteNaoEncontradoException")
    void buscarMetricasVinculadasAoAgenteDeveLancarAgenteNaoEncontradoException() {
        //Arrange:
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.metricasDispositivoService.buscarMetricasVinculadasAoAgente(1L));

        verify(this.metricasDispositivoMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("buscarMetricasVinculadasAoAgente() deve retornar uma lista vazia")
    void buscarMetricasVinculadasAoAgenteDeveRetornarListaVazia() {
        //Arrange:
        Agente agente = new AgenteBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.metricasDispositivoRepository.findByAgenteId(1L)).thenReturn(List.of());

        //Act:
        List<MetricasDispositivoResponseDTO> retorno = this.metricasDispositivoService.buscarMetricasVinculadasAoAgente(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());

    }

    @Test
    @DisplayName("buscarMetricasVinculadasAoAgente() deve retornar uma lista de MetricasDispositivoResponseDTO")
    void buscarMetricasVinculadasAoAgenteDeveRetornarListaDeMetricasDispositivoResponseDTO() {
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        MetricasDispositivo metricasDispositivo = new MetricasDispositivoBuilder().build();
        MetricasDispositivoResponseDTO responseDTO = new MetricasDispositivoResponseDTOBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.metricasDispositivoRepository.findByAgenteId(1L)).thenReturn(List.of(metricasDispositivo));
        when(this.metricasDispositivoMapper.toResponse(metricasDispositivo)).thenReturn(responseDTO);
        
        //Act:
        List<MetricasDispositivoResponseDTO> retorno = this.metricasDispositivoService.buscarMetricasVinculadasAoAgente(1L);

        //Assert:
        Assertions.assertNotNull(retorno);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals(MetricasTipo.BATERIA, retorno.get(0).getTipo()),
                () -> Assertions.assertEquals("50", retorno.get(0).getValor()),
                () -> Assertions.assertEquals("%", retorno.get(0).getUnidade())
        );
        verify(this.metricasDispositivoMapper, times(1)).toResponse(metricasDispositivo);
    }
}