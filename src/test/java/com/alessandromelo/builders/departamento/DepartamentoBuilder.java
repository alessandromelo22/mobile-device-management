package com.alessandromelo.builders.departamento;

import com.alessandromelo.entity.Departamento;

public class DepartamentoBuilder {

    private Long id = 1L;
    private String nome = "Recursos Humanos";
    private String sigla = "RH";


    public DepartamentoBuilder comId (Long id){
        this.id = id;
        return this;
    }

    public DepartamentoBuilder comNome (String nome){
        this.nome = nome;
        return this;
    }

    public DepartamentoBuilder comSigla (String sigla){
        this.sigla = sigla;
        return this;
    }


    public Departamento build () {
        Departamento departamento = new Departamento();
        departamento.setId(this.id);
        departamento.setNome(this.nome);
        departamento.setSigla(this.sigla);

        return departamento;
    }
}
