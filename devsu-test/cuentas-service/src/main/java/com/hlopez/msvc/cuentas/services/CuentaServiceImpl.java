package com.hlopez.msvc.cuentas.services;

import com.hlopez.msvc.cuentas.models.entity.Cuenta;
import com.hlopez.msvc.cuentas.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    private CuentaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listar() {
        return (List<Cuenta>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Cuenta guardar(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
