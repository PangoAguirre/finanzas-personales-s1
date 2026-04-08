package com.example.finanzas.dto;

public class EstadoEliminacionCuentaResponse {
    private boolean tieneSaldo;
    private boolean tieneTransacciones;
    private boolean requiereConfirmacion;

    public EstadoEliminacionCuentaResponse() {}

    public EstadoEliminacionCuentaResponse(boolean tieneSaldo, boolean tieneTransacciones) {
        this.tieneSaldo = tieneSaldo;
        this.tieneTransacciones = tieneTransacciones;
        this.requiereConfirmacion = tieneSaldo || tieneTransacciones;
    }

    public boolean isTieneSaldo() { return tieneSaldo; }
    public void setTieneSaldo(boolean tieneSaldo) { this.tieneSaldo = tieneSaldo; }
    public boolean isTieneTransacciones() { return tieneTransacciones; }
    public void setTieneTransacciones(boolean tieneTransacciones) { this.tieneTransacciones = tieneTransacciones; }
    public boolean isRequiereConfirmacion() { return requiereConfirmacion; }
    public void setRequiereConfirmacion(boolean requiereConfirmacion) { this.requiereConfirmacion = requiereConfirmacion; }
}
