package com.alessandromelo.exception.importacao;

public class CsvLeituraException extends RuntimeException {

    public CsvLeituraException(String message) {
        super(message);
    }

    public CsvLeituraException() {
        super("O arquivo CSV contém erros de validação. " +
                "Verifique o cabeçalho, campos obrigatórios, linhas vazias e formato dos dados.");
    }
}
