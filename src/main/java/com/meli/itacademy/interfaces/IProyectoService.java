package com.meli.itacademy.interfaces;

import com.meli.itacademy.models.Proyecto;

import java.util.Collection;

public interface IProyectoService {

    Collection<Proyecto> getProyectos();
    Proyecto getProyecto(int id);
    void addProyecto(Proyecto proyecto);
    Proyecto editProyecto(Proyecto proyecto);
    void deleteProyecto(int id);
}
