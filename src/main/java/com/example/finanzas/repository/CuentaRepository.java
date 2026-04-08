package com.example.finanzas.repository;

import com.example.finanzas.entity.Cuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("""
            select c from Cuenta c
            join UsuarioCuenta uc on uc.cuenta.id = c.id
            where uc.usuario.id = :usuarioId and c.activo = true
            order by c.id
            """)
    List<Cuenta> findActiveByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("""
            select c from Cuenta c
            join UsuarioCuenta uc on uc.cuenta.id = c.id
            where uc.usuario.id = :usuarioId and c.id = :cuentaId
            """)
    Optional<Cuenta> findOwnedByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("cuentaId") Long cuentaId);
}
