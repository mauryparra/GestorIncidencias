package com.meli.itacademy;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.meli.itacademy.models.Usuario;
import com.meli.itacademy.server.StandardResponse;
import com.meli.itacademy.server.StatusResponse;
import com.meli.itacademy.services.UsuarioServiceMapImpl;

public class GestorIncidenciasMain {

    public static void main(String[] args) {

        final UsuarioServiceMapImpl usuarioService = new UsuarioServiceMapImpl();

        // Setup de datos iniciales

        String[] nombres = {"Mauricio", "Juan", "Maria"};
        String[] apellidos = {"Parra", "Gonzalez,", "Martinez"};

        for (int i = 0; i < 3; i++)
        {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombres[i]);
            usuario.setApellido(apellidos[i]);

            usuarioService.addUsuario(usuario);
        }

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
            Usuario usuarioModificar = usuarioService.editUsuario(usuarioEditado);

            if (usuarioModificar != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(usuarioModificar))
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
    }
}
