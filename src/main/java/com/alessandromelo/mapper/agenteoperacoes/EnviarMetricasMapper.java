package com.alessandromelo.mapper.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasRequestDTO;
import com.alessandromelo.dto.agenteoperacoes.enviarmetricas.EnviarMetricasResponseDTO;
import com.alessandromelo.entity.MetricasDispositivo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnviarMetricasMapper {

    MetricasDispositivo toEntity (EnviarMetricasRequestDTO enviarMetricasRequestDTO);


    EnviarMetricasResponseDTO toResponse(MetricasDispositivo metricasDispositivo);
}
