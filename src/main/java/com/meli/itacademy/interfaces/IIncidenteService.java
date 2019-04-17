package com.meli.itacademy.interfaces;

import com.meli.itacademy.models.Incidente;

import java.util.Collection;

public interface IIncidenteService {

    Collection<Incidente> getIncidentes();
    Incidente getIncidente(int id);
    void addIncidente(Incidente incidente);
    Incidente addDescripcion(int id, String descripcion);
    Incidente setSolucionado(int id);
}
