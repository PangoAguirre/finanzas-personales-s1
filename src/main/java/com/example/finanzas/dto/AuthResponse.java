package com.example.finanzas.dto;

public class AuthResponse {
    private String token;
    private String tipo = "Bearer";
    private Long usuarioId;
    private String nombre;

    public AuthResponse() {}

    public AuthResponse(String token, Long usuarioId, String nombre) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
