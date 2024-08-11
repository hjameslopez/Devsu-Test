package com.hlopez.msvc.cuentas.services;

import com.hlopez.msvc.cuentas.models.entity.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {
    List<Movimiento> listar();
    Optional<Movimiento> porId(Long id);
    Movimiento guardar(Movimiento movimiento);
    void eliminar(Long id);
}
