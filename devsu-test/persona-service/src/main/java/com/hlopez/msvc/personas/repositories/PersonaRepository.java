package com.hlopez.msvc.personas.repositories;

import com.hlopez.msvc.personas.models.entity.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonaRepository extends CrudRepository<Persona,Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);

}
