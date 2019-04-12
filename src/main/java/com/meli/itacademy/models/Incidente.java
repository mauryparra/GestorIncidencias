package com.meli.itacademy.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Incidente {

    private int id;
    private ClasificacionEnum clasificacion;
    private String descripcion;
    private Usuario reportador;
    private Usuario responsable;
    private EstadoEnum estado;
    private LocalDateTime fechaCreado;
    private LocalDateTime fechaCierre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClasificacionEnum getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionEnum clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getReportador() {
        return reportador;
    }

    public void setReportador(Usuario reportador) {
        this.reportador = reportador;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}
