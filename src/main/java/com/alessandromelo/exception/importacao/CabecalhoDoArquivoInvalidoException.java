package com.alessandromelo.exception.importacao;

public class CabecalhoDoArquivoInvalidoException extends RuntimeException {
    public CabecalhoDoArquivoInvalidoException() {
        super("Cabeçalho do arquivo inválido!");
    }
}
