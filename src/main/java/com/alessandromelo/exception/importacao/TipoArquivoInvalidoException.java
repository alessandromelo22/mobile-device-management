package com.alessandromelo.exception.importacao;

public class TipoArquivoInvalidoException extends RuntimeException {
    public TipoArquivoInvalidoException() {
        super("Tipo de arquivo inválido!");
    }
}
