package com.alessandromelo.service;

import com.alessandromelo.builders.agente.AgenteBuilder;
import com.alessandromelo.builders.agente.AgenteRequestDTOBuilder;
import com.alessandromelo.builders.agente.AgenteResponseDTOBuilder;
import com.alessandromelo.builders.agente.AgenteResumoResponseDTOBuilder;
import com.alessandromelo.builders.dispositivo.DispositivoBuilder;
import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.Dispositivo;
import com.alessandromelo.enums.AgenteStatus;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.exception.dispositivo.DispositivoNaoEncontradoException;
import com.alessandromelo.mapper.AgenteMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.DispositivoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgenteServiceTest {

    @InjectMocks
    private AgenteService agenteService;

    @Mock
    private AgenteRepository agenteRepository;
    @Mock
    private AgenteMapper agenteMapper;
    @Mock
    private DispositivoRepository dispositivoRepository;

    @Captor
    private ArgumentCaptor<Agente> agenteCaptor;


    private final LocalDateTime DATA_FIXA = LocalDateTime.of(2025, 3, 5, 22, 10, 7);

    private final Clock clockFixo = Clock.fixed(DATA_FIXA.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    @BeforeEach
    void setUp(){

        agenteService = new AgenteService(this.agenteRepository, this.agenteMapper, this.dispositivoRepository, this.clockFixo);
    }


    /**<p><b>listarTodosAgentes():</b></p>
     *
     *  <p>1- Deve retornar uma lista vazia</p>
     *  <p>2- Deve retornar uma lista de AgenteResponseDTO</p>
     */
    @Test
    @DisplayName("listarTodosAgentes() deve retornar uma lista vazia")
    void listarTodosAgentesDeveRetornarListaVazia() {
        //Arrange:
        when(this.agenteRepository.findAll()).thenReturn(List.of());

        //Act:
        List<AgenteResponseDTO> retorno = this.agenteService.listarTodosAgentes();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }


    @Test
    @DisplayName("listarTodosAgentes() deve retornar uma lista de AgenteResponseDTO")
    void listarTodosAgentesDeveRetornarListaDeAgenteResponseDTO() {
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder().build();

        when(this.agenteRepository.findAll()).thenReturn(List.of(agente));
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);


        //Act:
        List<AgenteResponseDTO> retorno = this.agenteService.listarTodosAgentes();

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", retorno.get(0).getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.get(0).getStatus())
        );

        verify(this.agenteMapper, times(1)).toResponseDTO(agente);
    }



    /**<p><b>buscarAgentePorId():</b></p>
     *
     *  <p>1- Deve lançar AgenteNaoEncontradoException</p>
     *  <p>2- Deve retornar um AgenteResponseDTO</p>
     */
    @Test
    @DisplayName("buscarAgentePorId() deve lançar AgenteNaoEncontradoException")
    void buscarAgentePorIdDeveLancarAgenteNaoEncontradoException (){
        //Arrange:
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act:
        //Assert:
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteService.buscarAgentePorId(1L));

        verify(this.agenteMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("buscarAgentePorId() deve retornar um AgenteResponseDTO")
    void buscarAgentePorIdDeveRetornarAgenteResponseDTO (){
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);

        //Act:
        AgenteResponseDTO retorno = this.agenteService.buscarAgentePorId(1L);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", retorno.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus())
        );
    }



    /**<p><b>buscarAgentesPorStatus():</b></p>
     *
     *  <p>1- Deve retornar lista vazia</p>
     *  <p>2- Deve retornar uma lista de AgenteResponseDTO</p>
     */
    @Test
    @DisplayName("buscarAgentesPorStatus() deve retornar lista vazia")
    void buscarAgentesPorStatusDeveRetornarListaVazia (){
        //Arrange:
        when(this.agenteRepository.findByStatus(AgenteStatus.ATIVO)).thenReturn(List.of());

        //Act:
        List<AgenteResponseDTO> retorno = this.agenteService.buscarAgentesPorStatus(AgenteStatus.ATIVO);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertTrue(retorno.isEmpty());
    }

    @Test
    @DisplayName("buscarAgentesPorStatus() deve retornar lista de AgenteResponseDTO")
    void buscarAgentesPorStatusDeveRetornarListaDeAgenteResponseDTO (){
        //Arrange:
        Agente agente = new AgenteBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder().build();

        when(this.agenteRepository.findByStatus(AgenteStatus.ATIVO)).thenReturn(List.of(agente));
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);

        //Act:
        List<AgenteResponseDTO> retorno = this.agenteService.buscarAgentesPorStatus(AgenteStatus.ATIVO);

        //Assert:
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.get(0).getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", retorno.get(0).getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.get(0).getStatus())
        );

        verify(this.agenteMapper, times(1)).toResponseDTO(agente);
    }



    /**<p><b>cadastrarNovoAgente():</b></p>
     *
     *  <p>1 -Deve lançar DispositivoNaoEncontradoException </p>
     *  <p>2 -Deve retornar AgenteResponseDTO </p>
     *  <p>3 -Deve retornar AgenteResponseDTO sem o objeto Dispositivo </p>
     */
    @Test
    @DisplayName("cadastrarNovoAgente() deve lançar DispositivoNaoEncontradoException")
    void cadastrarNovoAgenteDeveLancarDispositivoNaoEncontradoException(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder().comDispositivoId(1L).build();
        Agente agente = new AgenteBuilder().build();

        when(this.agenteMapper.toEntity(requestDTO)).thenReturn(agente);
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Asert
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> agenteService.cadastrarNovoAgente(requestDTO));

        verify(this.agenteRepository,Mockito.never()).save(any());
    }

    @Test
    @DisplayName("cadastrarNovoAgente() deve retornar AgentenResponseDTO")
    void cadastrarNovoAgenteDeveRetornarAgenteResponseDTO(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder().comDispositivoId(1L).build();
        Agente agente = new AgenteBuilder().comDataUltimaAtividade(DATA_FIXA).build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder()
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteMapper.toEntity(requestDTO)).thenReturn(agente);
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);


        //Act
        AgenteResponseDTO retorno = this.agenteService.cadastrarNovoAgente(requestDTO);

        //Asert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals("BETA V1.0.1", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade()),

                () -> Assertions.assertEquals(1L, capturado.getDispositivo().getId()),
                () -> Assertions.assertEquals("Aspire 5", capturado.getDispositivo().getModelo()),
                () -> Assertions.assertEquals("Acer", capturado.getDispositivo().getMarca()),
                () -> Assertions.assertEquals("6977a67dey0", capturado.getDispositivo().getNumeroSerie())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals("BETA V1.0.1", retorno.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }

    @Test
    @DisplayName("cadastrarNovoAgente() deve retornar AgentenResponseDTO sem objeto Dispositivo")
    void cadastrarNovoAgenteDeveRetornarAgenteResponseDTOSemObjetoDispositivo(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder().build();
        Agente agente = new AgenteBuilder().comDataUltimaAtividade(DATA_FIXA).build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder()
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteMapper.toEntity(requestDTO)).thenReturn(agente);
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);


        //Act
        AgenteResponseDTO retorno = this.agenteService.cadastrarNovoAgente(requestDTO);

        //Asert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals("BETA V1.0.1", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade()),

                () -> Assertions.assertNull(capturado.getDispositivo())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals("BETA V1.0.1", retorno.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }

    /**<p><b>atualizarAgente():</b></p>
     *
     *  <p>1-Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2-Deve lançar DispositivoNaoEncontradoException </p>
     *  <p>3-Deve retornar AgenteResponseDTO </p>
     *  <p>4-Deve retornar AgenteResponseDTO sem o objeto Dispositivo</p>
     */
    @Test
    @DisplayName("atualizarAgente() deve lançar AgenteNaoEncontradoException")
    void atualizarAgenteDeveLancarAgenteNaoEncontradoException(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder()
                .comVersao("BETA V2.2.2").build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Assert
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteService.atualizarAgente(1L,requestDTO));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarAgente() deve lançar DispositivoNaoEncontradoException")
    void atualizarAgenteDeveLancarDispositivoNaoEncontradoException(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder()
                .comVersao("BETA V2.2.2")
                .comDispositivoId(1L).build();

        Agente agente = new AgenteBuilder().build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Assert
        Assertions.assertThrows(DispositivoNaoEncontradoException.class,
                () -> this.agenteService.atualizarAgente(1L,requestDTO));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("atualizarAgente() deve retornar AgenteResponseDTO")
    void atualizarAgenteDeveRetornarAgenteResponseDTO(){
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder()
                .comVersao("BETA V2.2.2")
                .comDispositivoId(1L).build();

        Agente agente = new AgenteBuilder().build();
        Dispositivo dispositivo = new DispositivoBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder()
                .comVersao("BETA V2.2.2")
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.dispositivoRepository.findById(1L)).thenReturn(Optional.of(dispositivo));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);

        //Act
        AgenteResponseDTO retorno = this.agenteService.atualizarAgente(1L, requestDTO);

        //Assert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade()),

                () -> Assertions.assertEquals(1L, capturado.getDispositivo().getId()),
                () -> Assertions.assertEquals("Aspire 5", capturado.getDispositivo().getModelo()),
                () -> Assertions.assertEquals("Acer", capturado.getDispositivo().getMarca()),
                () -> Assertions.assertEquals("6977a67dey0", capturado.getDispositivo().getNumeroSerie())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", requestDTO.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }

    @Test
    @DisplayName("atualizarAgente() deve retornar AgenteResponseDTO sem objeto Dispositivo")
    void atualizarAgenteDeveRetornarAgenteResponseDTOSemObjetoDispositivo() {
        //Arrange
        AgenteRequestDTO requestDTO = new AgenteRequestDTOBuilder()
                .comVersao("BETA V2.2.2").build();

        Agente agente = new AgenteBuilder().build();
        AgenteResponseDTO responseDTO = new AgenteResponseDTOBuilder()
                .comVersao("BETA V2.2.2")
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResponseDTO(agente)).thenReturn(responseDTO);

        //Act
        AgenteResponseDTO retorno = this.agenteService.atualizarAgente(1L, requestDTO);

        //Assert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade()),

                () -> Assertions.assertNull(capturado.getDispositivo())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals("BETA V2.2.2", requestDTO.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }



    /**<p><b>desativarAgente():</b></p>
     *
     *  <p>1-Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2-Deve retornar AgenteResponseDTO </p>
     */
    @Test
    @DisplayName("desativarAgente() deve lançar AgenteNaoEncontradoException")
    void desativarAgenteDeveLancarAgenteNaoEncontradoException(){
        //Arrange
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Assert
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteService.desativarAgente(1L));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("desativarAgente() deve retornar AgenteResponseDTO")
    void desativarAgenteDeveRetornarAgenteResponseDTO(){
        //Arrange
        Agente agente = new AgenteBuilder().build();
        AgenteResumoResponseDTO responseDTO = new AgenteResumoResponseDTOBuilder()
                .comStatus(AgenteStatus.INATIVO)
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResumoResponseDTO(agente)).thenReturn(responseDTO);

        //Act
        AgenteResumoResponseDTO retorno = this.agenteService.desativarAgente(1L);

        //Assert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.INATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals(AgenteStatus.INATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }



    /**<p><b>ativarAgente():</b></p>
     *
     *  <p>1-Deve lançar AgenteNaoEncontradoException </p>
     *  <p>2-Deve retornar AgenteResponseDTO </p>
     */
    @Test
    @DisplayName("ativarAgente() deve lançar AgenteNaoEncontradoException")
    void ativarAgenteDeveLancarAgenteNaoEncontradoException(){
        //Arrange
        when(this.agenteRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        //Assert
        Assertions.assertThrows(AgenteNaoEncontradoException.class,
                () -> this.agenteService.ativarAgente(1L));

        verify(this.agenteRepository, never()).save(any());
    }

    @Test
    @DisplayName("ativarAgente() deve retornar AgenteResponseDTO")
    void ativarAgenteDeveRetornarAgenteResponseDTO(){
        //Arrange
        Agente agente = new AgenteBuilder().build();
        AgenteResumoResponseDTO responseDTO = new AgenteResumoResponseDTOBuilder()
                .comDataUltimaAtividade(DATA_FIXA).build();

        when(this.agenteRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(this.agenteRepository.save(agente)).thenReturn(agente);
        when(this.agenteMapper.toResumoResponseDTO(agente)).thenReturn(responseDTO);

        //Act
        AgenteResumoResponseDTO retorno = this.agenteService.ativarAgente(1L);

        //Assert
        Assertions.assertNotNull(retorno);

        verify(this.agenteRepository).save(this.agenteCaptor.capture());
        Agente capturado = this.agenteCaptor.getValue();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, capturado.getId()),
                () -> Assertions.assertEquals("BETA V1.0.1", capturado.getVersao()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, capturado.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, capturado.getDataUltimaAtividade())
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, retorno.getId()),
                () -> Assertions.assertEquals(AgenteStatus.ATIVO, retorno.getStatus()),
                () -> Assertions.assertEquals(DATA_FIXA, retorno.getDataUltimaAtividade())
        );
    }
}