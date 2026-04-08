package com.example.finanzas.service;

import com.example.finanzas.dto.CuentaRequest;
import com.example.finanzas.dto.CuentaResponse;
import com.example.finanzas.dto.EstadoEliminacionCuentaResponse;
import com.example.finanzas.entity.Cuenta;
import com.example.finanzas.entity.Usuario;
import com.example.finanzas.entity.UsuarioCuenta;
import com.example.finanzas.entity.UsuarioCuentaId;
import com.example.finanzas.entity.UsuarioCuentaPermiso;
import com.example.finanzas.exception.BusinessException;
import com.example.finanzas.exception.UnauthorizedException;
import com.example.finanzas.repository.CuentaRepository;
import com.example.finanzas.repository.TransaccionRepository;
import com.example.finanzas.repository.UsuarioCuentaRepository;
import com.example.finanzas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioCuentaRepository usuarioCuentaRepository;
    private final TransaccionRepository transaccionRepository;

    public CuentaService(CuentaRepository cuentaRepository,
                        UsuarioRepository usuarioRepository,
                        UsuarioCuentaRepository usuarioCuentaRepository,
                        TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioCuentaRepository = usuarioCuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional
    public CuentaResponse create(Long usuarioId, CuentaRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(request.getNombre().trim());
        cuenta.setTipo(request.getTipo());
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setActivo(true);
        cuenta = cuentaRepository.save(cuenta);

        UsuarioCuenta usuarioCuenta = new UsuarioCuenta();
        usuarioCuenta.setId(new UsuarioCuentaId(usuario.getId(), cuenta.getId()));
        usuarioCuenta.setUsuario(usuario);
        usuarioCuenta.setCuenta(cuenta);
        usuarioCuenta.setPermiso(UsuarioCuentaPermiso.PROPIETARIO);
        usuarioCuentaRepository.save(usuarioCuenta);

        return toResponse(cuenta);
    }

    public List<CuentaResponse> list(Long usuarioId) {
        return cuentaRepository.findActiveByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public CuentaResponse update(Long usuarioId, Long cuentaId, CuentaRequest request) {
        Cuenta cuenta = cuentaRepository.findOwnedByUsuarioId(usuarioId, cuentaId)
                .orElseThrow(() -> new UnauthorizedException("La cuenta no existe o no pertenece al usuario"));

        if (!Boolean.TRUE.equals(cuenta.getActivo())) {
            throw new BusinessException("La cuenta está inactiva");
        }

        cuenta.setNombre(request.getNombre().trim());
        cuenta.setTipo(request.getTipo());
        return toResponse(cuentaRepository.save(cuenta));
    }

    public EstadoEliminacionCuentaResponse getDeleteState(Long usuarioId, Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findOwnedByUsuarioId(usuarioId, cuentaId)
                .orElseThrow(() -> new UnauthorizedException("La cuenta no existe o no pertenece al usuario"));
        boolean tieneSaldo = cuenta.getSaldo() != null && cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0;
        boolean tieneTransacciones = transaccionRepository.existsByCuentaId(cuentaId);
        return new EstadoEliminacionCuentaResponse(tieneSaldo, tieneTransacciones);
    }

    @Transactional
    public void delete(Long usuarioId, Long cuentaId, boolean confirmacionExplicita) {
        Cuenta cuenta = cuentaRepository.findOwnedByUsuarioId(usuarioId, cuentaId)
                .orElseThrow(() -> new UnauthorizedException("La cuenta no existe o no pertenece al usuario"));

        EstadoEliminacionCuentaResponse estado = getDeleteState(usuarioId, cuentaId);
        if (estado.isRequiereConfirmacion() && !confirmacionExplicita) {
            throw new BusinessException("La cuenta tiene saldo o transacciones asociadas. Debes confirmar la eliminación.");
        }

        cuenta.setActivo(false);
        cuentaRepository.save(cuenta);
    }

    private CuentaResponse toResponse(Cuenta cuenta) {
        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNombre(cuenta.getNombre());
        response.setTipo(cuenta.getTipo());
        response.setSaldo(cuenta.getSaldo());
        response.setActivo(cuenta.getActivo());
        response.setFechaCreacion(cuenta.getFechaCreacion());
        return response;
    }
}
