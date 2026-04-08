package com.example.finanzas.service;

import com.example.finanzas.dto.AuthResponse;
import com.example.finanzas.dto.LoginRequest;
import com.example.finanzas.dto.RegisterRequest;
import com.example.finanzas.entity.*;
import com.example.finanzas.exception.BusinessException;
import com.example.finanzas.security.CustomUserDetails;
import com.example.finanzas.security.JwtService;
import com.example.finanzas.security.TokenBlacklistService;
import com.example.finanzas.repository.CuentaRepository;
import com.example.finanzas.repository.UsuarioCuentaRepository;
import com.example.finanzas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Locale;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;
    private final UsuarioCuentaRepository usuarioCuentaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthService(UsuarioRepository usuarioRepository,
                       CuentaRepository cuentaRepository,
                       UsuarioCuentaRepository usuarioCuentaRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       TokenBlacklistService tokenBlacklistService) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
        this.usuarioCuentaRepository = usuarioCuentaRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String correo = request.getCorreo().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.findByCorreoIgnoreCase(correo).isPresent()) {
            throw new BusinessException("Este correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre().trim());
        usuario.setCorreo(correo);
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(UsuarioRol.USUARIO_ESTANDAR);
        usuario.setMoneda("COP");
        usuario.setActivo(true);
        usuario.setFechaRegistro(OffsetDateTime.now());
        usuario = usuarioRepository.save(usuario);

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Efectivo");
        cuenta.setTipo(CuentaTipo.EFECTIVO);
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setActivo(true);
        cuenta = cuentaRepository.save(cuenta);

        UsuarioCuenta usuarioCuenta = new UsuarioCuenta();
        usuarioCuenta.setId(new UsuarioCuentaId(usuario.getId(), cuenta.getId()));
        usuarioCuenta.setUsuario(usuario);
        usuarioCuenta.setCuenta(cuenta);
        usuarioCuenta.setPermiso(UsuarioCuentaPermiso.PROPIETARIO);
        usuarioCuentaRepository.save(usuarioCuenta);

        CustomUserDetails details = new CustomUserDetails(usuario);
        String token = jwtService.generateToken(details);
        return new AuthResponse(token, usuario.getId(), usuario.getNombre());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo().trim().toLowerCase(Locale.ROOT), request.getPassword()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            return new AuthResponse(token, userDetails.getId(), userDetails.getNombre());
        } catch (DisabledException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        }
    }

    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String token = authHeader.substring(7);
        tokenBlacklistService.blacklist(token, jwtService.extractExpiration(token).toInstant());
    }
}
