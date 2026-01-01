package com.alessandromelo.exception.importacao;

public class ArquivoImportacaoException extends RuntimeException {

    public ArquivoImportacaoException(String message) {
        super(message);
    }

    public ArquivoImportacaoException() {
        super("Não foi possível ler o arquivo enviado. Verifique se o arquivo está íntegro e tente novamente.");
    }
}
