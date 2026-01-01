package com.alessandromelo.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String matricula;

    private String cargo;

    private Boolean ativo = true; //indica se o usuario está ativo no sistema

    @OneToMany(mappedBy = "usuario")
    private List<Dispositivo> dispositivos;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento; //FK


    public Usuario() {
    }

    public Usuario(String nome, String email, String matricula, String cargo, Boolean ativo, List<Dispositivo> dispositivos, Departamento departamento) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.cargo = cargo;
        this.ativo = ativo;
        this.dispositivos = dispositivos;
        this.departamento = departamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
