package com.example.finanzas.repository;

import com.example.finanzas.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    boolean existsByCuentaId(Long cuentaId);
}
