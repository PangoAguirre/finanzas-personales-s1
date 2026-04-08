package com.example.finanzas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioCuentaId implements Serializable {
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "cuenta_id")
    private Long cuentaId;

    public UsuarioCuentaId() {}

    public UsuarioCuentaId(Long usuarioId, Long cuentaId) {
        this.usuarioId = usuarioId;
        this.cuentaId = cuentaId;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioCuentaId that)) return false;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(cuentaId, that.cuentaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, cuentaId);
    }
}
