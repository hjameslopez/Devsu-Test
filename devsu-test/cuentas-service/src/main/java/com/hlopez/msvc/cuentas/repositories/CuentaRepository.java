package com.hlopez.msvc.cuentas.repositories;

import com.hlopez.msvc.cuentas.models.entity.Cuenta;
import org.springframework.data.repository.CrudRepository;

public interface CuentaRepository extends CrudRepository<Cuenta, Long> {
}
