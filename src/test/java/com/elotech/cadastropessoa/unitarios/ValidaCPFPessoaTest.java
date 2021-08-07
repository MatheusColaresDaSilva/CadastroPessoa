package com.elotech.cadastropessoa.unitarios;

import com.elotech.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidaCPFPessoaTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    public void testeCpfValidoPessoa() {
        Assertions.assertTrue(pessoaService.isCpfValido("91795184000"),"Cpf Valido");
    }

   @Test
    public void testeCpfInvalidoPessoa() {
        Assertions.assertFalse(pessoaService.isCpfValido("91795166000"),"Cpf Invalido por numero");
        Assertions.assertFalse(pessoaService.isCpfValido("91795A84000"),"Cpf Invalido com caractere indesejado");
        Assertions.assertFalse(pessoaService.isCpfValido("917951840000"),"Cpf Invalido por quantidade de numero > 11");
        Assertions.assertFalse(pessoaService.isCpfValido("9179518400"),"Cpf Invalido por quantidade de numero < 11");
    }
}
