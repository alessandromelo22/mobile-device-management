package com.alessandromelo.exception.importacao;

public class ArquivoNaoEnviadoException extends RuntimeException {

    public ArquivoNaoEnviadoException() {
        super("Arquivo não envidado na requisição!");
    }
}
