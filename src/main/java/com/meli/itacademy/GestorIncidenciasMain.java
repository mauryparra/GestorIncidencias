package com.meli.itacademy;

import static spark.Spark.*;
import com.google.gson.Gson;

import com.meli.itacademy.models.*;
import com.meli.itacademy.server.ApiRouter;
import com.meli.itacademy.server.StandardResponse;
import com.meli.itacademy.server.StatusResponse;

import java.time.LocalDateTime;

public class GestorIncidenciasMain {

    public static void main(String[] args) {
        // Setup de datos iniciales
        setTestData();

        port(4567);

        path("/api", () -> {
            path("/usuario", () -> {
                get("", ApiRouter::getUsuarios);
                get("/:id", ApiRouter::getUsuario);
                post("", ApiRouter::addUsuario);
                put("", ApiRouter::editUsuario);
                delete("/:id", ApiRouter::deleteUsuario);
            });

            path("/proyecto", () -> {
                get("", ApiRouter::getProyectos);
                get("/:id", ApiRouter::getProyecto);
                post("", ApiRouter::addProyecto);
                put("", ApiRouter::editProyecto);
                delete("/:id", ApiRouter::deleteProyecto);

            });

            path("/incidente", () -> {
                get("", ApiRouter::getIncidentes);
                get("/:id", ApiRouter::getIncidente);
                post("", ApiRouter::addIncidente);
                put("", ApiRouter::appendDescripcionIncidente);
                options("", ApiRouter::solveIncidente);
            });
        });

        notFound((req, res) -> {
            res.type("application/json");
            res.status(404);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                    "Recurso no encontrado"));
        });
    }

    private static void setTestData() {
        // Cargar Usuarios de Prueba
        String[] nombres = {"Mauricio", "Juan", "Maria"};
        String[] apellidos = {"Parra", "Gonzalez,", "Martinez"};

        for (int i = 0; i < nombres.length; i++)
        {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombres[i]);
            usuario.setApellido(apellidos[i]);

            ApiRouter.usuarioService.addUsuario(usuario);
        }

        // Cargar Proyectos de Prueba
        String[] titulos = {"Proyecto de Prueba", "Proyecto Preliminar"};

        for (int i = 0; i < titulos.length; i++)
        {
            Proyecto proyecto = new Proyecto();
            proyecto.setTitulo(titulos[i]);
            proyecto.setPropietario(ApiRouter.usuarioService.getUsuario(i + 1));

            ApiRouter.proyectoService.addProyecto(proyecto);
        }

        // Cargar Incidentes de Prueba

        String[] descripciones = {"Problema para agregar Usuarios", "Problema al cargar vistas"};
        ClasificacionEnum[] clasificaciones = {ClasificacionEnum.CRITICO, ClasificacionEnum.NORMAL};
        LocalDateTime[] fechas = {null, LocalDateTime.now()};

        for (int i = 0; i < descripciones.length; i++)
        {
            Incidente incidente = new Incidente();
            incidente.setClasificacion(clasificaciones[i]);
            incidente.setDescripcion(descripciones[i]);
            incidente.setEstado(fechas[i] == null ? EstadoEnum.ASIGNADO : EstadoEnum.RESUELTO);
            incidente.setProyecto(ApiRouter.proyectoService.getProyecto(i + 1));
            incidente.setReportador(ApiRouter.usuarioService.getUsuario(i + 1));
            incidente.setResponsable(ApiRouter.usuarioService.getUsuario(3));
            incidente.setFechaCreado(LocalDateTime.now());
            incidente.setFechaCierre(fechas[i]);

            ApiRouter.incidenteService.addIncidente(incidente);
        }
    }
}
