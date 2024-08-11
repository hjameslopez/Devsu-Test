package com.hlopez.msvc.personas.services;

import com.hlopez.msvc.personas.models.entity.Persona;
import org.aspectj.weaver.patterns.PerObject;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    List<Persona> listar();
    Optional<Persona> porId(Long id);
    Persona guardar(Persona persona);
    void eliminar(Long id);

    Optional<Persona> porIdentificacion(String identificacion);
    boolean existePorIdentificacion(String identificacion);
    boolean existePorId(Long id);
}
