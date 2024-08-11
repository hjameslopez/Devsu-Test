package com.hlopez.msvc.cuentas.services;

import com.hlopez.msvc.cuentas.models.entity.Movimiento;
import com.hlopez.msvc.cuentas.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService{

    @Autowired
    private MovimientoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> listar() {
        return (List<Movimiento>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movimiento> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Movimiento guardar(Movimiento movimiento) {
        return repository.save(movimiento);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
