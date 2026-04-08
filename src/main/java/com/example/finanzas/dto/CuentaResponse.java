package com.example.finanzas.dto;

import com.example.finanzas.entity.CuentaTipo;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class CuentaResponse {
    private Long id;
    private String nombre;
    private CuentaTipo tipo;
    private BigDecimal saldo;
    private Boolean activo;
    private OffsetDateTime fechaCreacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public CuentaTipo getTipo() { return tipo; }
    public void setTipo(CuentaTipo tipo) { this.tipo = tipo; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public OffsetDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(OffsetDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
