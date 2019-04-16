package com.meli.itacademy.server;

import com.google.gson.Gson;
import com.meli.itacademy.models.Proyecto;
import com.meli.itacademy.models.Usuario;
import com.meli.itacademy.services.ProyectoServiceMapImpl;
import com.meli.itacademy.services.UsuarioServiceMapImpl;
import spark.Request;
import spark.Response;

public abstract class ApiRouter {

    public static final UsuarioServiceMapImpl usuarioService = new UsuarioServiceMapImpl();
    public static final ProyectoServiceMapImpl proyectoService = new ProyectoServiceMapImpl();


    // USUARIOS

    /**
     * Obtener todos los usuarios
     * @param request
     * @param response
     * @return
     */
    public static String getUsuarios(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(usuarioService.getUsuarios())));
    }

    /**
     * Obtener un usuario por id
     * @param request
     * @param response
     * @return
     */
    public static String getUsuario(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(usuarioService.getUsuario(Integer.parseInt(request.params(":id"))))));

    }

    /**
     * Añadir un usuario
     * @param request
     * @param response
     * @return
     */
    public static String addUsuario(Request request, Response response) {
        response.type("application/json");

        Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
        usuarioService.addUsuario(usuario);

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
    }

    /**
     * Editar Usuario
     * @param request
     * @param response
     * @return
     */
    public static String editUsuario(Request request, Response response) {
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
    }

    /**
     * Eliminar Usuario
     * @param request
     * @param response
     * @return
     */
    public static String deleteUsuario(Request request, Response response) {
        response.type("application/json");

        usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")));
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                "Usuario Borrado."));
    }

    /**
     * Obtinene todos los proyectos
     * @param request
     * @param response
     * @return
     */
    public static String getProyectos(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(proyectoService.getProyectos())));
    }

    /**
     * Obtiene un proyecto por id
     * @param request
     * @param response
     * @return
     */
    public static String getProyecto(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(proyectoService.getProyecto(Integer.parseInt(request.params(":id"))))));
    }

    /**
     * Añadir un nuevo proyecto
     * @param request
     * @param response
     * @return
     */
    public static String addProyecto(Request request, Response response) {
        response.type("application/json");

        Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
        proyecto.setPropietario(usuarioService.getUsuario(proyecto.getPropietario().getId()));
        proyectoService.addProyecto(proyecto);

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
    }

    /**
     * Editar un proyecto
     * @param request
     * @param response
     * @return
     */
    public static String editProyecto(Request request, Response response) {
        response.type("application/json");

        Proyecto proyectoEditado = new Gson().fromJson(request.body(), Proyecto.class);

        // proyectoEditado.setPropietario(usuarioService.getUsuario(proyectoEditado.getPropietario().getId()));
        Proyecto proyectoCambiado = proyectoService.editProyecto(proyectoEditado);

        System.out.println(proyectoCambiado.getTitulo());

        if (proyectoCambiado != null) {
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoCambiado)));
        } else {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                    "Error al modificar proyecto."));
        }

    }





    /**
     * Eliminar un proyecto por id
     * @param request
     * @param response
     * @return
     */
    public static String deleteProyecto(Request request, Response response) {
        response.type("application/json");

        proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")));
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                "Proyecto Borrado."));
    }


}
