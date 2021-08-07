package com.elotech.exception;

public class DataNascimentoFuturaException extends BusinessException{

    public DataNascimentoFuturaException() {
        super("Data de nascimento superior a data atual");
    }
}
