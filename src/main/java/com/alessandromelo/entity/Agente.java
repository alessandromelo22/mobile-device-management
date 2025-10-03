package com.alessandromelo.entity;

import com.alessandromelo.enums.AgenteStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String versao;

    @Enumerated(EnumType.STRING)
    private AgenteStatus status; // enum (ATIVO, INATIVO, EM_EXECUCAO)

    private String log; // Log de execução do agente
    private LocalDateTime dataUltimaAtividade;
//  private String ip;
//  private String tipoConexao;

    @OneToOne
    @JoinColumn(name = "id_dispositivo")
    private Dispositivo dispositivo; //FK

    @OneToMany(mappedBy = "agente")
    private List<Comando> comandos;



    public Agente() {
    }

    public Agente(Long id, String versao, AgenteStatus status, String log, LocalDateTime dataUltimaAtividade, Dispositivo dispositivo, List<Comando> comandos) {
        this.id = id;
        this.versao = versao;
        this.status = status;
        this.log = log;
        this.dataUltimaAtividade = dataUltimaAtividade;
        this.dispositivo = dispositivo;
        this.comandos = comandos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public AgenteStatus getStatus() {
        return status;
    }

    public void setStatus(AgenteStatus status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getDataUltimaAtividade() {
        return dataUltimaAtividade;
    }

    public void setDataUltimaAtividade(LocalDateTime dataUltimaAtividade) {
        this.dataUltimaAtividade = dataUltimaAtividade;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    public void setComandos(List<Comando> comandos) {
        this.comandos = comandos;
    }
}
