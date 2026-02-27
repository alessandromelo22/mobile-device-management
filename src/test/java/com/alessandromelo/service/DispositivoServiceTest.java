package com.alessandromelo.service;

import com.alessandromelo.builders.usuario.UsuarioBuilder;
import com.alessandromelo.builders.usuario.UsuarioResponseDTOBuilder;
import com.alessandromelo.dto.usuario.UsuarioResponseDTO;
import com.alessandromelo.entity.Usuario;
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


    /**<p><b>listarDispositivos():</b></p>
     *
     *  <p>1- Deve retornar uma lista de DispositivoResponseDTO</p>
     *  <p>2- Deve retornar uma lista vazia</p>
     */
    @Test
    @DisplayName("listarTodosDispositivos() deve retornar uma lista de DispositivoResponseDTO")
    void listarTodosDispositivosDeveRetornarListaDeDispositivoResponseDTO() {
        //Arrange:
        Dispositivo dispositivo = new DispostivoBuilder().build();
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
}