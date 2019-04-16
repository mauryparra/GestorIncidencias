package com.meli.itacademy;

import static spark.Spark.*;
import com.google.gson.Gson;

import com.meli.itacademy.models.Proyecto;
import com.meli.itacademy.models.Usuario;
import com.meli.itacademy.server.ApiRouter;
import com.meli.itacademy.server.StandardResponse;
import com.meli.itacademy.server.StatusResponse;
import com.meli.itacademy.services.ProyectoServiceMapImpl;
import com.meli.itacademy.services.UsuarioServiceMapImpl;

public class GestorIncidenciasMain {

    private static final UsuarioServiceMapImpl usuarioService = new UsuarioServiceMapImpl();
    private static final ProyectoServiceMapImpl proyectoService = new ProyectoServiceMapImpl();

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

        System.out.println(ApiRouter.proyectoService.getProyecto(1).getPropietario().getNombre() + " " + ApiRouter.proyectoService.getProyecto(1).getPropietario().getApellido());

        ApiRouter.usuarioService.getUsuario(1).setNombre("Marco");
        ApiRouter.usuarioService.getUsuario(1).setApellido("Polo");

        System.out.println(ApiRouter.proyectoService.getProyecto(1).getPropietario().getNombre() + " " + ApiRouter.proyectoService.getProyecto(1).getPropietario().getApellido());
    }
}
