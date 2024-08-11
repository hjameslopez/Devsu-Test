package com.hlopez.msvc.personas.controllers;

import com.hlopez.msvc.personas.dtos.ClienteDTO;
import com.hlopez.msvc.personas.exceptions.ClienteNotFoundException;
import com.hlopez.msvc.personas.exceptions.ClienteRegistradoException;
import com.hlopez.msvc.personas.exceptions.IdentificadorException;
import com.hlopez.msvc.personas.models.entity.Cliente;
import com.hlopez.msvc.personas.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> listar(){
        return service.listar();
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Cliente> clienteOptional = service.porId(id);
        if(clienteOptional.isPresent()){
            return ResponseEntity.ok(clienteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        try {
            Cliente cliente = service.porId(id);
            return ResponseEntity.ok(cliente);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        if (!cliente.getIdentificacion().isEmpty() && service.existePorIdentificacion(cliente.getIdentificacion())){
            //return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","El identificador ya se encuentra registrado!"));
            throw new IdentificadorException();
        }
        if (service.existePorClienteid(cliente.getClienteid())){
            //return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","El ID del cliente ya se encuentra registrado!"));
            throw new ClienteRegistradoException();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Cliente> c = service.optionalPorId(id);
        if(c.isPresent()){
            Cliente clienteDb = c.get();
            if (!cliente.getIdentificacion().isEmpty() && !cliente.getIdentificacion().equalsIgnoreCase(clienteDb.getIdentificacion()) && service.existePorIdentificacion(cliente
                    .getIdentificacion())){
                //return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","El identificador ya se encuentra registrado!"));
                throw new IdentificadorException();
            }
            clienteDb.setNombre(cliente.getNombre());
            clienteDb.setGenero(cliente.getGenero());
            clienteDb.setEdad(cliente.getEdad());
            clienteDb.setIdentificacion(cliente.getIdentificacion());
            clienteDb.setTelefono(cliente.getTelefono());
            clienteDb.setContraseña(cliente.getContraseña());
            clienteDb.setEstado(cliente.getEstado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(clienteDb));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@RequestBody ClienteDTO dto, @PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.optionalPorId(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            if (dto.getNombre() != null) cliente.setNombre(dto.getNombre());
            if (dto.getGenero() != null) cliente.setGenero(dto.getGenero());
            if (dto.getEdad() != null) cliente.setEdad(dto.getEdad());
            if (dto.getIdentificacion() != null) cliente.setIdentificacion(dto.getIdentificacion());
            if (dto.getTelefono() != null) cliente.setTelefono(dto.getTelefono());
            if (dto.getContraseña() != null) cliente.setContraseña(dto.getContraseña());
            if (dto.getEstado() != null) cliente.setEstado(dto.getEstado());

            return ResponseEntity.status(HttpStatus.OK).body(service.guardar(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Cliente> p = service.optionalPorId(id);
        if(p.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
/*
    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnoPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listaPorId(ids));
    }

*/
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {

            errores.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
