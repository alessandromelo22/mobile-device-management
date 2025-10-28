package com.alessandromelo.mapper;

import com.alessandromelo.dto.departamento.DepartamentoRequestDTO;
import com.alessandromelo.dto.departamento.DepartamentoResponseDTO;
import com.alessandromelo.entity.Departamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    //Request -> Entity
    Departamento toEntity(DepartamentoRequestDTO departamentoRequestDTO);

    //Entity -> Response
    DepartamentoResponseDTO toResponseDTO(Departamento departamento);



}
