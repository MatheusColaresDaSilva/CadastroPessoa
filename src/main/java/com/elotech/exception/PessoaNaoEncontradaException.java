package com.elotech.exception;

public class PessoaNaoEncontradaException extends BusinessException{

    public PessoaNaoEncontradaException() {
        super("Pessoa não encontrada");
    }
}
