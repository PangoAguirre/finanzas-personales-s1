package com.example.finanzas.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuario_cuenta", schema = "finanzas")
public class UsuarioCuenta {
    @EmbeddedId
    private UsuarioCuentaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cuentaId")
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "permiso", nullable = false, length = 20)
    private UsuarioCuentaPermiso permiso = UsuarioCuentaPermiso.PROPIETARIO;

    @Column(name = "fecha_asignacion", nullable = false)
    private OffsetDateTime fechaAsignacion = OffsetDateTime.now();

    public UsuarioCuentaId getId() {
        return id;
    }

    public void setId(UsuarioCuentaId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public UsuarioCuentaPermiso getPermiso() {
        return permiso;
    }

    public void setPermiso(UsuarioCuentaPermiso permiso) {
        this.permiso = permiso;
    }

    public OffsetDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(OffsetDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
