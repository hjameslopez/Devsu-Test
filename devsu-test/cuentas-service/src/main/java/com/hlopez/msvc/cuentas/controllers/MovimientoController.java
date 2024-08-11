package com.hlopez.msvc.cuentas.controllers;

import com.hlopez.msvc.cuentas.dtos.MovimientoDTO;
import com.hlopez.msvc.cuentas.models.entity.Movimiento;
import com.hlopez.msvc.cuentas.services.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public List<Movimiento> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Movimiento> movimientoOptional = service.porId(id);
        if(movimientoOptional.isPresent()){
            return ResponseEntity.ok(movimientoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Movimiento movimiento, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(movimiento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Movimiento movimiento, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Movimiento> c = service.porId(id);
        if(c.isPresent()){
            Movimiento movimientoDb = c.get();
            movimientoDb.setFecha(movimiento.getFecha());
            movimientoDb.setTipoMovimiento(movimiento.getTipoMovimiento());
            movimientoDb.setValor(movimiento.getValor());
            movimientoDb.setSaldo(movimiento.getSaldo());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(movimientoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@RequestBody MovimientoDTO dto, @PathVariable Long id) {
        Optional<Movimiento> cuentaOptional = service.porId(id);
        if (cuentaOptional.isPresent()) {
            Movimiento movimiento = cuentaOptional.get();

            if (dto.getFecha() != null) movimiento.setFecha(dto.getFecha());
            if (dto.getTipoMovimiento() != null) movimiento.setTipoMovimiento(dto.getTipoMovimiento());
            if (dto.getValor() != null) movimiento.setValor(dto.getValor());
            if (dto.getSaldo() != null) movimiento.setSaldo(dto.getSaldo());

            return ResponseEntity.status(HttpStatus.OK).body(service.guardar(movimiento));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Movimiento> m = service.porId(id);
        if(m.isPresent()){
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
