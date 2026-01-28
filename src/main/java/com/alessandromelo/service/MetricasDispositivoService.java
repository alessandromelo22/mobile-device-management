package com.alessandromelo.service;

import com.alessandromelo.dto.metricasdispositivo.MetricasDispositivoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.entity.MetricasDispositivo;
import com.alessandromelo.exception.agente.AgenteNaoEncontradoException;
import com.alessandromelo.mapper.MetricasDispositivoMapper;
import com.alessandromelo.repository.AgenteRepository;
import com.alessandromelo.repository.MetricasDispositivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricasDispositivoService {

    private MetricasDispositivoRepository metricasDispositivoRepository;
    private AgenteRepository agenteRepository;
    private MetricasDispositivoMapper metricasDispositivoMapper;


    public MetricasDispositivoService(MetricasDispositivoRepository metricasDispositivoRepository, AgenteRepository agenteRepository, MetricasDispositivoMapper metricasDispositivoMapper) {
        this.metricasDispositivoRepository = metricasDispositivoRepository;
        this.agenteRepository = agenteRepository;
        this.metricasDispositivoMapper = metricasDispositivoMapper;
    }



    //Get
    public List<MetricasDispositivoResponseDTO> buscarMetricasVinculadasAoAgente (Long agenteId){

        Agente agente = this.agenteRepository.findById(agenteId)
                .orElseThrow(() -> new AgenteNaoEncontradoException(agenteId));

        List<MetricasDispositivo> metricasDispositivos = this.metricasDispositivoRepository.findByAgenteId(agenteId);

        return metricasDispositivos.stream().map(this.metricasDispositivoMapper::toResponse).toList();
    }


//Delete
    public void deletarMetricasDispositivoPorId(Long metricasDispositivoId){
        this.metricasDispositivoRepository.deleteById(metricasDispositivoId);
    }

}
