package com.meli.itacademy;

import static spark.Spark.*;
import com.google.gson.Gson;

import com.meli.itacademy.models.Proyecto;
import com.meli.itacademy.models.Usuario;
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

        // Obtener todos los usuarios
        get("/usuario", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuarios())));
        });

        // Obtener un usuario por id
        get("/usuario/:id", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuario(Integer.parseInt(request.params(":id"))))));
        });

        // Crear un nuevo usuario
        post("/usuario", (request, response) -> {
            response.type("application/json");

            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            usuarioService.addUsuario(usuario);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        // Modificar usuario
        put("/usuario", (request, response) -> {
            response.type("application/json");

            Usuario usuarioEditado = new Gson().fromJson(request.body(), Usuario.class);
            Usuario usuarioCambiado = usuarioService.editUsuario(usuarioEditado);

            if (usuarioCambiado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(usuarioCambiado))
                );
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                        "Error al editar el usuario.")
                );
            }
        });

        // Eliminar usuario
        delete("/usuario/:id", (request, response) -> {
            response.type("application/json");

            usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    "Usuario Borrado."));
        });

        // Obtener todos los proyectos
        get("/proyecto", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyectos())));
        });

        // Obtener un proyecto por id
        get("/proyecto/:id", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyecto(Integer.parseInt(request.params(":id"))))));
        });

        // Crear un nuevo proyecto
        post("/proyecto", (request, response) -> {
            response.type("application/json");

            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        // Modificar proyecto
        put("/proyecto", (request, response) -> {
            response.type("application/json");

            Proyecto proyectoEditado = new Gson().fromJson(request.body(), Proyecto.class);
            Proyecto proyectoCambiado = proyectoService.editProyecto(proyectoEditado);

            if (proyectoCambiado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectoCambiado)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                        "Error al modificar proyecto."));
            }
        });

        // Eliminar proyecto
        delete("/proyecto/:id", (request, response) -> {
            response.type("application/json");

            proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    "Proyecto Borrado."));
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

            usuarioService.addUsuario(usuario);
        }

        // Cargar Proyectos de Prueba
        String[] titulos = {"Proyecto de Prueba", "Proyecto Preliminar"};

        for (int i = 0; i < titulos.length; i++)
        {
            Proyecto proyecto = new Proyecto();
            proyecto.setTitulo(titulos[i]);
            proyecto.setPropietario(usuarioService.getUsuario(i + 1));

            proyectoService.addProyecto(proyecto);
        }
    }
}
