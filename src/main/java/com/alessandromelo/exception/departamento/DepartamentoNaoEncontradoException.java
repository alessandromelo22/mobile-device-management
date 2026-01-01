package com.alessandromelo.exception.departamento;

public class DepartamentoNaoEncontradoException extends RuntimeException {

    public DepartamentoNaoEncontradoException(Long departamentoId) {
        super("Departamento com o id " + departamentoId + " não encontrado!");
    }

    public DepartamentoNaoEncontradoException(String nomeDepartamento) {
        super("Departamento com o nome " + nomeDepartamento + " não encontrado!");
    }
}
