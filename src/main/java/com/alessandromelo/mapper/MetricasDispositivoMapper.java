package com.alessandromelo.mapper;

import com.alessandromelo.dto.metricasdispositivo.MetricasDispositivoResponseDTO;
import com.alessandromelo.entity.MetricasDispositivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MetricasDispositivoMapper {


    @Mapping(target = "agenteId", source = "agente.id")
    MetricasDispositivoResponseDTO toResponse (MetricasDispositivo metricasDispositivo);
}
