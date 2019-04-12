package com.meli.itacademy.services;

import com.meli.itacademy.interfaces.IProyectoService;
import com.meli.itacademy.models.Proyecto;

import java.util.Collection;
import java.util.HashMap;

public class ProyectoServiceMapImpl implements IProyectoService {

    private HashMap<Integer, Proyecto> proyectoMapImpl;

    public ProyectoServiceMapImpl() {
        this.proyectoMapImpl = new HashMap<>();
    }

    @Override
    public Collection<Proyecto> getProyectos() {
        return proyectoMapImpl.values();
    }

    @Override
    public Proyecto getProyecto(int id) {
        return proyectoMapImpl.get(id);
    }

    @Override
    public void addProyecto(Proyecto proyecto) {
        proyecto.setId(proyectoMapImpl.size() + 1);
        proyectoMapImpl.put(proyecto.getId(), proyecto);
    }

    @Override
    public Proyecto editProyecto(Proyecto proyecto) {
        Proyecto proyectoEditar = proyectoMapImpl.get(proyecto.getId());

        if (proyectoEditar.getTitulo() != proyecto.getTitulo())
        {
            proyectoEditar.setTitulo(proyecto.getTitulo());
        }

        if (proyectoEditar.getPropietario() != proyecto.getPropietario())
        {
            proyectoEditar.setPropietario(proyecto.getPropietario());
        }

        return proyectoEditar;
    }

    @Override
    public void deleteProyecto(int id) {
        proyectoMapImpl.remove(id);
    }
}
