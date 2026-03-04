package com.alessandromelo.builders.usuario;

import com.alessandromelo.dto.usuario.UsuarioImportDTO;

public class UsuarioImportDTOBuilder {

    private String nome = "Jorge da Silva"; //Obrigatorio
    private String email = "jorginds69@gmail.com"; //Obrigatorio
    private String matricula = "7001"; //Obrigatorio
    private String cargo = "Analista de RH";
    private String nomeDepartamento = "Recursos Humanos"; //(FK)


    public UsuarioImportDTOBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioImportDTOBuilder comEmail(String email){
        this.email = email;
        return this;
    }

    public UsuarioImportDTOBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }

    public UsuarioImportDTOBuilder comCargo(String cargo){
        this.cargo = cargo;
        return this;
    }

    public UsuarioImportDTOBuilder comNomeDepartamento(String nomeDepartamento){
        this.nomeDepartamento = nomeDepartamento;
        return this;
    }

    public UsuarioImportDTO build(){
        UsuarioImportDTO usuarioImportDTO = new UsuarioImportDTO();
        usuarioImportDTO.setNome(this.nome);
        usuarioImportDTO.setEmail(this.email);
        usuarioImportDTO.setMatricula(this.matricula);
        usuarioImportDTO.setCargo(this.cargo);
        usuarioImportDTO.setNomeDepartamento(this.nomeDepartamento);

        return usuarioImportDTO;
    }
}
