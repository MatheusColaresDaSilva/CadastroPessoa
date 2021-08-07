package com.elotech.controller;

import com.elotech.dto.response.ErroResponseDTO;
import com.elotech.dto.response.ResponseDTO;
import com.elotech.exception.BusinessException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

public abstract class BaseController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDTO<Object>> handleException(BusinessException ex) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErro(erroResponseDTO));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDTO<Object>> handleException(ConstraintViolationException ex) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErro(erroResponseDTO));
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseDTO<Object>> handleException(JdbcSQLIntegrityConstraintViolationException ex) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErro(erroResponseDTO));
    }

}
