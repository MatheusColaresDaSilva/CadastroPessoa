package com.elotech.cadastropessoa.unitarios;

import com.elotech.business.RegraValidator;
import com.elotech.exception.CpfInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ValidaCPFPessoaTest {

    private RegraValidator regraValidator = new RegraValidator();

    @Test
    public void testeCpfValidoPessoa() {
        assertDoesNotThrow(() -> {
            regraValidator.isCpfValido("91795184000");
        });
    }

  @Test
    public void testeCpfInvalidoPessoa() {
      Exception exception = assertThrows(CpfInvalidoException.class, () -> {
          regraValidator.isCpfValido("91795166000");
      });

      String expectedMessage = "CPF Inválido";
      String actualMessage = exception.getMessage();

      assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testeCpfInvalidoPessoaCaracterIndesejado() {
        Exception exception = assertThrows(CpfInvalidoException.class, () -> {
            regraValidator.isCpfValido("91795A84000");
        });

        String expectedMessage = "CPF Inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testeCpfInvalidoPessoaQuantidaDeDigitosMaiorOnze() {
        Exception exception = assertThrows(CpfInvalidoException.class, () -> {
            regraValidator.isCpfValido("917951840000");
        });

        String expectedMessage = "CPF Inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testeCpfInvalidoPessoaQuantidaDeDigitosMenosOnze() {
        Exception exception = assertThrows(CpfInvalidoException.class, () -> {
            regraValidator.isCpfValido("9179518400");
        });

        String expectedMessage = "CPF Inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
