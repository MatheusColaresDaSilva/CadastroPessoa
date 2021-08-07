package com.elotech.cadastropessoa.unitarios;

import com.elotech.business.RegraValidator;
import com.elotech.exception.DataNascimentoFuturaException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidaDataNascimentoTest {

    private RegraValidator regraValidator = new RegraValidator();

    @Test
    public void validaDataNascimentoMaiorQueDataAtual() {
        Exception exception = assertThrows(DataNascimentoFuturaException.class, () -> {
           LocalDate dateTime = LocalDate.of(2021,02,10);
           LocalDate dateAniversario = LocalDate.of(2021,03,10);
           regraValidator.isDataNascimentoValida(dateAniversario, dateTime);
        });

        String expectedMessage = "Data de nascimento superior a data atual";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validaDataNascimentoIgualQueDataAtual() {
        assertDoesNotThrow(() -> {
            LocalDate dateTime = LocalDate.of(2021,02,10);
            LocalDate dateAniversario = LocalDate.of(2021,02,10);
            regraValidator.isDataNascimentoValida(dateAniversario, dateTime);
        });
    }

    @Test
    public void validaDataNascimentoMenorQueDataAtual() {
        assertDoesNotThrow(() -> {
            LocalDate dateTime = LocalDate.of(2021,02,10);
            LocalDate dateAniversario = LocalDate.of(2021,01,10);
            regraValidator.isDataNascimentoValida(dateAniversario, dateTime);
        });
    }

}
