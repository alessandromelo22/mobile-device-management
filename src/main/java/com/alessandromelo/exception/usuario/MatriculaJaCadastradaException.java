package com.alessandromelo.exception.usuario;

public class MatriculaJaCadastradaException extends RuntimeException {

    public MatriculaJaCadastradaException() {
        super("A matricula informada já existe no sistema!");
    }

    public MatriculaJaCadastradaException(String matricula){
        super("A matrícula " + matricula + " já existe no sistema!");
    }
}
