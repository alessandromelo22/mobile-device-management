package com.alessandromelo.exception.importacao;

public class TamanhoDoArquivoExcedidoException extends RuntimeException {
    public TamanhoDoArquivoExcedidoException() {
        super("O arquivo deve ter no máximo 5MB");
    }
}
