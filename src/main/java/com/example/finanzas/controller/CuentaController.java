package com.example.finanzas.controller;

import com.example.finanzas.dto.CuentaRequest;
import com.example.finanzas.dto.CuentaResponse;
import com.example.finanzas.dto.EstadoEliminacionCuentaResponse;
import com.example.finanzas.dto.MessageResponse;
import com.example.finanzas.security.CustomUserDetails;
import com.example.finanzas.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cuentas")
@Tag(name = "Cuentas")
@SecurityRequirement(name = "bearerAuth")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    @Operation(summary = "Crear cuenta")
    public ResponseEntity<CuentaResponse> create(@AuthenticationPrincipal CustomUserDetails user,
                                                 @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(cuentaService.create(user.getId(), request));
    }

    @GetMapping
    @Operation(summary = "Listar cuentas activas del usuario")
    public ResponseEntity<List<CuentaResponse>> list(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(cuentaService.list(user.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cuenta")
    public ResponseEntity<CuentaResponse> update(@AuthenticationPrincipal CustomUserDetails user,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(cuentaService.update(user.getId(), id, request));
    }

    @GetMapping("/{id}/estado-eliminacion")
    @Operation(summary = "Consultar si la cuenta requiere confirmación para eliminar")
    public ResponseEntity<EstadoEliminacionCuentaResponse> getDeleteState(@AuthenticationPrincipal CustomUserDetails user,
                                                                          @PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.getDeleteState(user.getId(), id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cuenta con soft delete")
    public ResponseEntity<MessageResponse> delete(@AuthenticationPrincipal CustomUserDetails user,
                                                  @PathVariable Long id,
                                                  @RequestParam(defaultValue = "false") boolean confirm) {
        cuentaService.delete(user.getId(), id, confirm);
        return ResponseEntity.ok(new MessageResponse("Cuenta eliminada correctamente"));
    }
}
