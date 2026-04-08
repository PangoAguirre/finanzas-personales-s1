package com.example.finanzas.dto;

import com.example.finanzas.entity.CuentaTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CuentaRequest {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotNull
    private CuentaTipo tipo;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public CuentaTipo getTipo() { return tipo; }
    public void setTipo(CuentaTipo tipo) { this.tipo = tipo; }
}
