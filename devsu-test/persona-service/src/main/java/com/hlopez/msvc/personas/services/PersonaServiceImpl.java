package com.hlopez.msvc.personas.services;

import com.hlopez.msvc.personas.models.entity.Persona;
import com.hlopez.msvc.personas.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService{

    @Autowired
    private PersonaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listar() {
        return (List<Persona>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Persona guardar(Persona persona) {
        return repository.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> porIdentificacion(String identificacion) {
        return repository.findByIdentificacion(identificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorIdentificacion(String identificacion) {
        return repository.existsByIdentificacion(identificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
