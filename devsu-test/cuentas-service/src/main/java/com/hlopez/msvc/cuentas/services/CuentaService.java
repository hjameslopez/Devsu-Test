package com.hlopez.msvc.cuentas.services;

import com.hlopez.msvc.cuentas.models.entity.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {
    List<Cuenta> listar();
    Optional<Cuenta> porId(Long id);
    Cuenta guardar(Cuenta cuenta);
    void eliminar(Long id);
}
