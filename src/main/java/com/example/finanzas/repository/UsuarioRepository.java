package com.example.finanzas.repository;

import com.example.finanzas.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u where lower(u.correo) = lower(:correo)")
    Optional<Usuario> findByCorreoIgnoreCase(@Param("correo") String correo);

    boolean existsByActivoTrueAndId(Long id);
}
