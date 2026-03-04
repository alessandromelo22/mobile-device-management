package com.alessandromelo.builders.dispositivo;

import com.alessandromelo.dto.dispositivo.DispositivoUsuarioResponseDTO;
import com.alessandromelo.dto.usuario.UsuarioResumoResponseDTO;
import com.alessandromelo.enums.DispositivoStatus;

public class DispositivoUsuarioResponseDTOBuilder {

    private Long dispositivoId = 1L;
    private String modelo = "Aspire 5";
    private DispositivoStatus status = DispositivoStatus.ATIVO;
    private UsuarioResumoResponseDTO usuarioResumoResponseDTO;



    public DispositivoUsuarioResponseDTOBuilder comDispositivoId (Long dispositivoId){
        this.dispositivoId = dispositivoId;
        return this;
    }

    public DispositivoUsuarioResponseDTOBuilder comModelo (String modelo){
        this.modelo = modelo;
        return this;
    }

    public DispositivoUsuarioResponseDTOBuilder comStatus (DispositivoStatus status){
        this.status = status;
        return this;
    }

    public DispositivoUsuarioResponseDTOBuilder comUsuarioResumoResponseDTO (UsuarioResumoResponseDTO usuarioResumoResponseDTO){
        this.usuarioResumoResponseDTO = usuarioResumoResponseDTO;
        return this;
    }

    public DispositivoUsuarioResponseDTO build (){
        DispositivoUsuarioResponseDTO responseDTO = new DispositivoUsuarioResponseDTO();
        responseDTO.setDispositivoId(this.dispositivoId);
        responseDTO.setModelo(this.modelo);
        responseDTO.setStatus(this.status);
        responseDTO.setUsuarioResumoResponseDTO(this.usuarioResumoResponseDTO);

        return responseDTO;
    }
}
