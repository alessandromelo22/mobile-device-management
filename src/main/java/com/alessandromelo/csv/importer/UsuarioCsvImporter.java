package com.alessandromelo.csv.importer;

import com.alessandromelo.csv.importer.validator.UsuarioCsvImporterValidator;
import com.alessandromelo.dto.usuario.UsuarioImportDTO;
import com.alessandromelo.exception.importacao.ArquivoImportacaoException;
import com.alessandromelo.exception.importacao.CsvLeituraException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class UsuarioCsvImporter {


    private UsuarioCsvImporterValidator csvImporterValidator;


    public UsuarioCsvImporter(UsuarioCsvImporterValidator csvImporterValidator) {
        this.csvImporterValidator = csvImporterValidator;
    }

    public List<UsuarioImportDTO> lerCsv(MultipartFile arquivo){

        try {

            this.csvImporterValidator.validar(arquivo);

            InputStream inputStream = arquivo.getInputStream();

            CsvToBean<UsuarioImportDTO> csvToBean = new CsvToBeanBuilder<UsuarioImportDTO>(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .withType(UsuarioImportDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            return csvToBean.parse();

        } catch (IOException ex) {
            throw new ArquivoImportacaoException();

        } catch (CsvValidationException ex){
            throw new CsvLeituraException();
        }
    }
}
