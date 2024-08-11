package com.hlopez.msvc.cuentas.exceptions;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String mensaje){
        super(mensaje);
    }
}
