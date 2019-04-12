package com.meli.itacademy.models;

public enum EstadoEnum {

    ASIGNADO ("Asignado"),
    RESUELTO ("Resuelto");

    private String estado;

    EstadoEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
