package com.meli.itacademy.interfaces;

import com.meli.itacademy.models.Incidente;

public interface IIncidenteService {

    void addIncidente(Incidente incidente);
    Incidente addDescripcion(int id, String descripcion);
    Incidente setSolucionado(int id);
}
