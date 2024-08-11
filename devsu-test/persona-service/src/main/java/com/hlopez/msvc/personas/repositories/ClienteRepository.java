package com.hlopez.msvc.personas.repositories;

import com.hlopez.msvc.personas.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente,Long> {
    Optional<Cliente> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
    boolean existsByClienteid(Long clienteid);
}
