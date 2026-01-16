package com.alessandromelo.service;

import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.entity.Departamento;
import com.alessandromelo.mapper.DepartamentoMapper;
import com.alessandromelo.repository.DepartamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;
    @Mock
    private DepartamentoMapper departamentoMapper;

    @InjectMocks
    private DepartamentoService departamentoService;



    @Test
    void deveRetornarDepartamentoIformadoPeloId(){
        //Arrange:
        Departamento departamentoEsperado = new Departamento();
        departamentoEsperado.setId(3L);
        departamentoEsperado.setNome("Compras");
        departamentoEsperado.setSigla("Cp");

        DepartamentoResponseDTO responseDTOEsperado = new DepartamentoResponseDTO();
        responseDTOEsperado.setId(3L);
        responseDTOEsperado.setNome("Compras");
        responseDTOEsperado.setSigla("Cp");


        Mockito.when(departamentoRepository.findById(3L)).thenReturn(Optional.of(departamentoEsperado));
        Mockito.when(departamentoMapper.toResponseDTO(departamentoEsperado)).thenReturn(responseDTOEsperado);

        //Act:
        DepartamentoResponseDTO resultado = this.departamentoService.buscarDepartamentoPorId(3L);


        //Assert:
        Assertions.assertEquals(responseDTOEsperado, resultado);


    }
  
}