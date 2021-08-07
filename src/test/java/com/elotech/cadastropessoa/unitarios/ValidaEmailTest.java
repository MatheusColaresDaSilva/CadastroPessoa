package com.elotech.cadastropessoa.unitarios;

import com.elotech.business.RegraValidator;
import com.elotech.exception.EmailInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidaEmailTest {

    private RegraValidator regraValidator = new RegraValidator();

    @Test
    public void validaEmailSemPontoCom() {
        Exception exception = assertThrows(EmailInvalidoException.class, () -> {
           regraValidator.isEmailValido("joao@gmail");
        });

        String expectedMessage = "Email Inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validaEmailSemPontoArroba() {
        Exception exception = assertThrows(EmailInvalidoException.class, () -> {
            regraValidator.isEmailValido("joao#gmail.com");
        });

        String expectedMessage = "Email Inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validaEmailComArrobaEPontoCom() {
        assertDoesNotThrow(() -> {
            regraValidator.isEmailValido("joao@gmail.com");
        });
    }

}
