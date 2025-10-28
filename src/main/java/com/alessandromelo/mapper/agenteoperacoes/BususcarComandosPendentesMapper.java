package com.alessandromelo.mapper.agenteoperacoes;

import com.alessandromelo.dto.agenteoperacoes.buscarcomandospendentes.BuscarComandosPendentesResponseDTO;
import com.alessandromelo.entity.Comando;
import com.alessandromelo.mapper.CocomandoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CocomandoMapper.class})
public interface BususcarComandosPendentesMapper {

    //Entity
    @Mapping(source = "agenteId", target = "agenteId")
    @Mapping(source = "comandos", target = "comandosResumoResponseDTO")
    BuscarComandosPendentesResponseDTO toResponseDTO(Long agenteId, List<Comando> comandos);

    //acho que ta certo, conferir o comando dps
}
