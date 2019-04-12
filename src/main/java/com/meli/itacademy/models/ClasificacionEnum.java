package com.meli.itacademy.models;

public enum ClasificacionEnum {

    CRITICO ("Crítico"),
    NORMAL ("Nomral"),
    MENOR ("Menor");

    private String clasificacion;

    ClasificacionEnum(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
