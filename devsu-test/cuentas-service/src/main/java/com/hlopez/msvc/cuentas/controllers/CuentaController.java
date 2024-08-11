package com.hlopez.msvc.cuentas.controllers;

import com.hlopez.msvc.cuentas.dtos.CuentaDTO;
import com.hlopez.msvc.cuentas.models.entity.Cuenta;
import com.hlopez.msvc.cuentas.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping
    public List<Cuenta> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Cuenta> cuentaOptional = service.porId(id);
        if(cuentaOptional.isPresent()){
            return ResponseEntity.ok(cuentaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Cuenta cuenta, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Cuenta> c = service.porId(id);
        if(c.isPresent()){
            Cuenta cuentaDb = c.get();
            cuentaDb.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDb.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDb.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDb.setEstado(cuenta.isEstado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cuentaDb));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@RequestBody CuentaDTO dto, @PathVariable Long id) {
        Optional<Cuenta> cuentaOptional = service.porId(id);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();

            if (dto.getNumeroCuenta() != null) cuenta.setNumeroCuenta(dto.getNumeroCuenta());
            if (dto.getTipoCuenta() != null) cuenta.setTipoCuenta(dto.getTipoCuenta());
            if (dto.getSaldoInicial() != null) cuenta.setSaldoInicial(dto.getSaldoInicial());
            if (dto.isEstado() != cuenta.isEstado()) cuenta.setEstado(dto.isEstado());

            return ResponseEntity.status(HttpStatus.OK).body(service.guardar(cuenta));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Cuenta> p = service.porId(id);
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
