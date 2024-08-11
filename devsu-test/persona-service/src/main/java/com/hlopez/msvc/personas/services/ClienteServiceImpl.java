package com.hlopez.msvc.personas.services;

import com.hlopez.msvc.personas.exceptions.ClienteNotFoundException;
import com.hlopez.msvc.personas.models.entity.Cliente;
import com.hlopez.msvc.personas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> optionalPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente porId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
    }


    @Override
    @Transactional
    public Cliente guardar(Cliente persona) {
        return repository.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorIdentificacion(String identificacion) {
        return repository.existsByIdentificacion(identificacion);
    }

    @Override
    public boolean existePorClienteid(Long clienteid) {
        return repository.existsByClienteid(clienteid);
    }
}
