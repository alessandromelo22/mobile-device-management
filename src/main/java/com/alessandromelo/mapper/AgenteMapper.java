package com.alessandromelo.mapper;

import com.alessandromelo.dto.agente.AgenteRequestDTO;
import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.agente.AgenteResumoResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.entity.Agente;
import com.alessandromelo.enums.AgenteStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AgenteMapper {


    //RequestDTO -> Entity
    public Agente toEntity(AgenteRequestDTO agenteRequestDTO){

        Agente agente = new Agente();

        agente.setVersao(agenteRequestDTO.getVersao());
        agente.setStatus(AgenteStatus.ATIVO); //setta o status como ATIVO por padrão
        agente.setLog(agenteRequestDTO.getLog());
        agente.setDataUltimaAtividade(LocalDateTime.now()); //setta a data e hora atual da chamada

        return agente;
    }


    //Entity -> ResponseDTO
    public AgenteResponseDTO toResponseDTO(Agente agente){

        AgenteResponseDTO agenteResponseDTO = new AgenteResponseDTO();

        agenteResponseDTO.setId(agente.getId());
        agenteResponseDTO.setVersao(agente.getVersao());
        agenteResponseDTO.setStatus(agente.getStatus());
        agenteResponseDTO.setLog(agente.getLog());
        agenteResponseDTO.setDataUltimaAtividade(agente.getDataUltimaAtividade());

        //Dispositivo
        if(agente.getDispositivo() != null){

            DispositivoResumoResponseDTO dispositivoResumoResponseDTO = new DispositivoResumoResponseDTO();
            dispositivoResumoResponseDTO.setId(agente.getDispositivo().getId());
            dispositivoResumoResponseDTO.setModelo(agente.getDispositivo().getModelo());
            dispositivoResumoResponseDTO.setStatus(agente.getDispositivo().getStatus());

            agenteResponseDTO.setDispositivoResumoResponseDTO(dispositivoResumoResponseDTO);

        }
        return agenteResponseDTO;
    }


    //Entity -> ResumoResponseDTO
    public AgenteResumoResponseDTO toResumoResponseDTO(Agente agente){

        AgenteResumoResponseDTO agenteResumoResponseDTO = new AgenteResumoResponseDTO();

        agenteResumoResponseDTO.setId(agente.getId());
        agenteResumoResponseDTO.setStatus(agente.getStatus());
        agenteResumoResponseDTO.setDataUltimaAtividade(agente.getDataUltimaAtividade());

        return agenteResumoResponseDTO;
    }
}
