package com.alessandromelo.dto.usuario;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioImportDTO {

    @NotBlank(message = "O nome do Usuário deve ser informado!")
    @CsvBindByName(column = "Nome")
    private String nome;

    @NotBlank(message = "O e-mail do Usuário deve ser informado!")
    @Email(message = "Formato de e-mail inválido!")
    @CsvBindByName(column = "Email")
    private String email;

    @NotBlank(message = "A matrícula do Usuário deve ser informada!")
    @CsvBindByName(column = "Matrícula")
    private String matricula;

    @CsvBindByName(column = "Cargo")
    private String cargo;

    @CsvBindByName(column = "Departamento")
    private String nomeDepartamento; //(FK)


    public UsuarioImportDTO() {
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

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
}
