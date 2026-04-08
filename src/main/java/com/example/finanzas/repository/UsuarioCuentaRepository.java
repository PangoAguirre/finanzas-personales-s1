package com.example.finanzas.repository;

import com.example.finanzas.entity.UsuarioCuenta;
import com.example.finanzas.entity.UsuarioCuentaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioCuentaRepository extends JpaRepository<UsuarioCuenta, UsuarioCuentaId> {
}
