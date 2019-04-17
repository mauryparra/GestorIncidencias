package com.meli.itacademy.services;

import com.meli.itacademy.interfaces.IIncidenteService;
import com.meli.itacademy.models.EstadoEnum;
import com.meli.itacademy.models.Incidente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;

public class IncidenteServiceMapImpl implements IIncidenteService {

    private HashMap<Integer, Incidente> incidenteMapImpl;

    public IncidenteServiceMapImpl() {
        this.incidenteMapImpl = new HashMap<>();
    }

    @Override
    public Collection<Incidente> getIncidentes() {
        return incidenteMapImpl.values();
    }

    @Override
    public Incidente getIncidente(int id) {
        return incidenteMapImpl.get(id);
    }

    @Override
    public void addIncidente(Incidente incidente) {
        incidente.setId(incidenteMapImpl.size() + 1);
        incidenteMapImpl.put(incidente.getId(), incidente);
    }

    @Override
    public Incidente addDescripcion(int id, String descripcion) {
        Incidente incidente = incidenteMapImpl.get(id);
        incidente.setDescripcion(incidente.getDescripcion() + " | " + descripcion);
        return incidente;
    }

    @Override
    public Incidente setSolucionado(int id) {
        Incidente incidente = incidenteMapImpl.get(id);
        incidente.setEstado(EstadoEnum.RESUELTO);
        incidente.setFechaCierre(LocalDateTime.now());
        return incidente;
    }
}
