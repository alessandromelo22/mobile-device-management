package com.alessandromelo.csv.importer.validator;

import com.alessandromelo.exception.importacao.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioCsvImporterValidatorTest {

    @InjectMocks
    private UsuarioCsvImporterValidator usuarioCsvImporterValidator;




    /**<p><b>validar():</b></p>
     *
     *  <p>1- Deve lançar ArquivoNaoEnviadoException</p>
     *  <p>2- Deve lançar ArquivoVazioException</p>
     *  <p>3- Deve lançar TamanhoDoArquivoExcedidoException</p>
     *  <p>4- Deve lancar TipoArquivoInvalidoException quando o contentType for null</p>
     *  <p>5- Deve lancar TipoArquivoInvalidoException quando o contentType for PDF</p>
     *  <p>6- Deve lancar TipoArquivoInvalidoException quando o contentType for PNG</p>
     *  <p>7- Deve lancar TipoArquivoInvalidoException quando o contentType for XML</p>
     *  <p>8- Deve lançar CabecalhoDoArquivoInvalidoException quando tiver espaços entre os valores</p>
     *  <p>9- Deve lançar CabecalhoDoArquivoInvalidoException quando faltar algum valor</p>
     *  <p>10- Deve lançar CabecalhoDoArquivoInvalidoException quando tiver algum valor trocado</p>
     *  <p>11- Deve executar sem problemas</p>
     */
    @Test
    @DisplayName("validar() deve lancar ArquivoNaoEnviadoException")
    void validarDeveLancarArquivoNaoEnviadoException() {
        //Arrange:
        //Act:
        //Assert:
        Assertions.assertThrows(ArquivoNaoEnviadoException.class,
                () -> this.usuarioCsvImporterValidator.validar(null));
    }

    @Test
    @DisplayName("validar() deve lancar ArquivoVazioException")
    void validarDeveLancarArquivoVazioException() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(true);

        //Act:
        //Assert:
        Assertions.assertThrows(ArquivoVazioException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lancar TamanhoDoArquivoExcedidoException")
    void validarDeveLancarTamanhoDoArquivoExcedidoException() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(5_242_881L); // aumentei apenas 1 no valor

        //Act:
        //Assert:
        Assertions.assertThrows(TamanhoDoArquivoExcedidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lancar TipoArquivoInvalidoException quando o contentType for null")
    void validarDeveLancarTipoArquivoInvalidoExceptionQuandoContentTypeForNull() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(5_242_880L);
        when(file.getContentType()).thenReturn(null);

        //Act:
        //Assert:
        Assertions.assertThrows(TipoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lancar TipoArquivoInvalidoException quando o contentType for PDF")
    void validarDeveLancarTipoArquivoInvalidoExceptionQuandoContentTypeForPDF() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(5_242_880L);
        when(file.getContentType()).thenReturn("application/pdf");

        //Act:
        //Assert:
        Assertions.assertThrows(TipoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lancar TipoArquivoInvalidoException quando o contentType for PNG")
    void validarDeveLancarTipoArquivoInvalidoExceptionQuandoContentTypeForPNG() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(5_242_880L);
        when(file.getContentType()).thenReturn("image/png");

        //Act:
        //Assert:
        Assertions.assertThrows(TipoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lancar TipoArquivoInvalidoException quando o contentType for XML")
    void validarDeveLancarTipoArquivoInvalidoExceptionQuandoContentTypeForXML() {
        //Arrange:
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(5_242_880L);
        when(file.getContentType()).thenReturn("application/xml");

        //Act:
        //Assert:
        Assertions.assertThrows(TipoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lançar CabecalhoDoArquivoInvalidoException quando tiver espaços entre os valores")
    void validarDeveLancarCabecalhoDoArquivoInvalidoExceptionQuandoTiverEspacosEntreOsValores() {
        //Arrange:
        String cabecalho = "Nome,Email, Matrícula,Cargo, Departamento";

        MultipartFile file = new MockMultipartFile(
                "file",
                "arquivoTeste.csv",
                "text/csv",
                cabecalho.getBytes(StandardCharsets.UTF_8));

        //Act:
        //Assert:
        Assertions.assertThrows(CabecalhoDoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lançar CabecalhoDoArquivoInvalidoException quando faltar algum valor")
    void validarDeveLancarCabecalhoDoArquivoInvalidoExceptionQuandoFaltarAlgumValor() {
        //Arrange:
        String cabecalho = "Nome,Email,Cargo,Departamento";

        MultipartFile file = new MockMultipartFile(
                "file",
                "arquivoTeste.csv",
                "text/csv",
                cabecalho.getBytes(StandardCharsets.UTF_8));

        //Act:
        //Assert:
        Assertions.assertThrows(CabecalhoDoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve lançar CabecalhoDoArquivoInvalidoException quando tiver algum valor trocado")
    void validarDeveLancarCabecalhoDoArquivoInvalidoExceptionQuandoTiverAlgumValorTrocado() {
        //Arrange:
        String cabecalho = "Nome,Departamento,Matrícula,Cargo,Email";

        MultipartFile file = new MockMultipartFile(
                "file",
                "arquivoTeste.csv",
                "text/csv",
                cabecalho.getBytes(StandardCharsets.UTF_8));

        //Act:
        //Assert:
        Assertions.assertThrows(CabecalhoDoArquivoInvalidoException.class,
                () -> this.usuarioCsvImporterValidator.validar(file));
    }

    @Test
    @DisplayName("validar() deve executar com sucesso")
    void validarDeveExecutarComSucesso() {
        //Arrange:
        String cabecalho = "Nome,Email,Matrícula,Cargo,Departamento";

        MultipartFile file = new MockMultipartFile(
                "file",
                "arquivoTeste.csv",
                "text/csv",
                cabecalho.getBytes(StandardCharsets.UTF_8));

        //Act:
        //Assert:
        Assertions.assertDoesNotThrow(() -> this.usuarioCsvImporterValidator.validar(file));
    }

}