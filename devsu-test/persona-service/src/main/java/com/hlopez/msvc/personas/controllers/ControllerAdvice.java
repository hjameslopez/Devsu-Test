package com.hlopez.msvc.personas.controllers;

import com.hlopez.msvc.personas.dtos.ErrorDTO;
import com.hlopez.msvc.personas.exceptions.IdentificadorException;
import com.hlopez.msvc.personas.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(IdentificadorException.class)
    public ResponseEntity<ErrorDTO> IdentificadorExceptionhandle(IdentificadorException ex) {
        ErrorDTO error = ErrorDTO.builder().code(Constantes.COD.EX_500).message(Constantes.MSG.EX_500).build();
        logger.error("Error -> ", ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdentificadorException.class)
    public ResponseEntity<ErrorDTO> ClienteRegistradoExceptionhandle(IdentificadorException ex) {
        ErrorDTO error = ErrorDTO.builder().code(Constantes.COD.EX_501).message(Constantes.MSG.EX_501).build();
        logger.error("Error -> ", ex);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
