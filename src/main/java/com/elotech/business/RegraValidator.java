package com.elotech.business;

import com.elotech.exception.CpfInvalidoException;
import com.elotech.exception.DataNascimentoFuturaException;

import java.time.LocalDate;

/**
Metodo para validar CPF.
 Algoritmo disponibilidado pela receita feredal (07/08/2021): http://www.receita.fazenda.gov.br/aplicacoes/atcta/cpf/funcoes.js

Outra opção seria usa a annotation org.hibernate.validator.constraints.br.CPF
**/
public class RegraValidator {

    public Boolean isCpfValido(String cpf) {

        RegexValidator regex = new RegexCpfValidator();
        regex.validator(cpf);

        int soma = 0;
        int resto;

        if (cpf == "00000000000") {
            throw new CpfInvalidoException();
        }

        for (int i=1; i<=9; i++) {
            soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (11 - i);
        }
        resto = (soma * 10) % 11;
        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }
        if (resto != Integer.parseInt(cpf.substring(9, 10)) ) {
            throw new CpfInvalidoException();
        }

        soma = 0;

        for (int i = 1; i <= 10; i++) {
            soma = soma + Integer.parseInt(cpf.substring(i-1, i)) * (12 - i);
        }
        resto = (soma * 10) % 11;
        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }
        if (resto != Integer.parseInt(cpf.substring(10, 11) ) ) {
            throw new CpfInvalidoException();
        }

        return true;
    }

    public Boolean isDataNascimentoValida(LocalDate dataA, LocalDate dataB) {
        if(!(dataA.isBefore(dataB) || dataA.isEqual(dataB))) {
            throw new DataNascimentoFuturaException();
        };

        return true;
    }

    public Boolean isEmailValido(String email) {
        RegexValidator regex = new RegexEmailValidator();
        return regex.validator(email);
    }

}
