package com.hlopez.msvc.personas.services;

import com.hlopez.msvc.personas.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();
    Optional<Cliente> optionalPorId(Long id);
    Cliente porId(Long id);
    Cliente guardar(Cliente persona);
    void eliminar(Long id);

    boolean existePorId(Long id);
    boolean existePorIdentificacion(String identificacion);
    boolean existePorClienteid(Long clienteid);
}
