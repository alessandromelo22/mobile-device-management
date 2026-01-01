package com.alessandromelo.exception.importacao;

public class ArquivoVazioException extends RuntimeException {
    public ArquivoVazioException() {
        super("Arquivo vazio!");
    }
}
