package com.hlopez.msvc.personas.controllers;

import com.hlopez.msvc.personas.models.entity.Persona;
import com.hlopez.msvc.personas.services.PersonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping
    public List<Persona> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Persona> personaOptional = service.porId(id);
        if(personaOptional.isPresent()){
            return ResponseEntity.ok(personaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Persona persona, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        if (!persona.getIdentificacion().isEmpty() && service.existePorIdentificacion(persona.getIdentificacion())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","El identificador ya se encuentra registrado!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(persona));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Persona persona, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Persona> p = service.porId(id);
        if(p.isPresent()){
            Persona personaDb = p.get();
            if (!persona.getIdentificacion().isEmpty() && !persona.getIdentificacion().equalsIgnoreCase(personaDb.getIdentificacion()) && service.porIdentificacion(persona.getIdentificacion()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","El identificador ya se encuentra registrado!"));
            }
            personaDb.setNombre(persona.getNombre());
            personaDb.setGenero(persona.getGenero());
            personaDb.setEdad(persona.getEdad());
            personaDb.setIdentificacion(persona.getIdentificacion());
            personaDb.setTelefono(persona.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(personaDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Persona> p = service.porId(id);
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
