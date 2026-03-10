package com.alessandromelo.builders.agente;

import com.alessandromelo.dto.agente.AgenteResponseDTO;
import com.alessandromelo.dto.dispositivo.DispositivoResumoResponseDTO;
import com.alessandromelo.enums.AgenteStatus;

import java.time.LocalDateTime;
import java.time.Month;

public class AgenteResponseDTOBuilder {

    private Long id = 1L;
    private String versao = "BETA V1.0.1"; //obrigatorio
    private AgenteStatus status = AgenteStatus.ATIVO;
    private LocalDateTime dataUltimaAtividade;
    private DispositivoResumoResponseDTO dispositivoResumoResponseDTO; //FK //obrigatorio


    public AgenteResponseDTOBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public AgenteResponseDTOBuilder comVersao(String versao){
        this.versao = versao;
        return this;
    }

    public AgenteResponseDTOBuilder comStatus(AgenteStatus status){
        this.status = status;
        return this;
    }

    public AgenteResponseDTOBuilder comDataUltimaAtividade(LocalDateTime dataUltimaAtividade){
        this.dataUltimaAtividade = dataUltimaAtividade;
        return this;
    }

    public AgenteResponseDTOBuilder comDispositivoResumoResponseDTO(DispositivoResumoResponseDTO dispositivoResumoResponseDTO){
        this.dispositivoResumoResponseDTO = dispositivoResumoResponseDTO;
        return this;
    }

    public AgenteResponseDTO build(){
        AgenteResponseDTO responseDTO = new AgenteResponseDTO();
        responseDTO.setId(this.id);
        responseDTO.setVersao(this.versao);
        responseDTO.setStatus(this.status);
        responseDTO.setDataUltimaAtividade(this.dataUltimaAtividade);
        responseDTO.setDispositivoResumoResponseDTO(this.dispositivoResumoResponseDTO);

        return responseDTO;
    }
}
