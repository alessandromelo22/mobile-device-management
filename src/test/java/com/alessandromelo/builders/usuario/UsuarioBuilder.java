package com.alessandromelo.builders.usuario;

import com.alessandromelo.entity.Departamento;
import com.alessandromelo.entity.Usuario;

public class UsuarioBuilder {

    private Long id = 1L;
    private String nome = "Jorge da Silva";
    private String email = "jorginds69@gmail.com";
    private String matricula = "7001";
    private Departamento departamento;

    public UsuarioBuilder comId(Long id){
        this.id = id;
        return this;
    }

    public UsuarioBuilder comNome(String nome){
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email){
        this.email = email;
        return this;
    }

    public UsuarioBuilder comMatricula(String matricula){
        this.matricula = matricula;
        return this;
    }

    public UsuarioBuilder comDepartamento(Departamento departamento){
        this.departamento = departamento;
        return this;
    }

    public Usuario build(){
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setMatricula(this.matricula);
        usuario.setDepartamento(this.departamento);

        return usuario;
    }
}
