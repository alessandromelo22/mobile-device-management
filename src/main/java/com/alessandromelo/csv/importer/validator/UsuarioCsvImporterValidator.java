package com.alessandromelo.csv.importer.validator;

import com.alessandromelo.exception.importacao.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class UsuarioCsvImporterValidator {


    private static final List<String> MIME_TYPES_ACEITOS = List.of(
            "text/csv",
            "application/vnd.ms-excel");

    private static final Long TAM_MAX = 5_242_880L; //Tamanho máximo que o arquivo deve ter 5MB

    private static final String[] CABECALHO_ESPERADO = {"Nome", "Email", "Matrícula", "Cargo", "Departamento"};



    //talvez retornar um Boolean e verificar esse boolean na classe de leitura pra retornar um List<T> vazio caso o arquivo esteja vazio.
    public void validar(MultipartFile arquivo) throws IOException, CsvValidationException {

        if (arquivo == null){
            throw new ArquivoNaoEnviadoException();
        }

        if (arquivo.isEmpty()){ //Verifica se o arquivo não está vazio
            throw new ArquivoVazioException();
        }

        if (arquivo.getSize() > TAM_MAX){ //Verifica se o arquivo é maior que 5MB
            throw new TamanhoDoArquivoExcedidoException();
        }

        if (arquivo.getContentType() == null || !MIME_TYPES_ACEITOS.contains(arquivo.getContentType())){ //Verifica se o tipo do arquivo foi informado e se o tipo é válido
            throw new TipoArquivoInvalidoException();
        }


        InputStream inputStream = arquivo.getInputStream();
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String[] cabecalho = csvReader.readNext();

        if(!(Arrays.equals(cabecalho, CABECALHO_ESPERADO))){ //Verifica se o cabeçalho contém os campos esperados
            throw new CabecalhoDoArquivoInvalidoException();
        }
    }
}
