package com.hlopez.msvc.personas.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona{


    @NotNull
    @Column(unique = true)
    private Long clienteid;

    @NotBlank
    private String contraseña;

    private boolean estado;

    public Cliente(){
        super();
    }

    public Cliente(Long clienteid, boolean estado, String contraseña) {
        this.clienteid = clienteid;
        this.estado = estado;
        this.contraseña = contraseña;
    }

    public Cliente(Long id, String nombre, String genero, int edad, String identificacion, String telefono, Long clienteid, boolean estado, String contraseña) {
        super(id, nombre, genero, edad, identificacion, telefono);
        this.clienteid = clienteid;
        this.estado = estado;
        this.contraseña = contraseña;
    }

    public @NotBlank String getContraseña() {
        return contraseña;
    }

    public void setContraseña(@NotBlank String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado() {
        return estado;
    }

    public @NotNull Long getClienteid() {
        return clienteid;
    }

    public void setClienteid(@NotNull Long clienteid) {
        this.clienteid = clienteid;
    }
}
