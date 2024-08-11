package com.hlopez.msvc.cuentas.exceptions;

public class CuentaNotFoundException extends RuntimeException{
    public CuentaNotFoundException(String mensaje){
        super(mensaje);
    }
}
