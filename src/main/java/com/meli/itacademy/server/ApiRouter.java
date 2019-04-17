package com.meli.itacademy.server;

import com.google.gson.Gson;
import com.meli.itacademy.models.Incidente;
import com.meli.itacademy.models.Proyecto;
import com.meli.itacademy.models.Usuario;
import com.meli.itacademy.services.IncidenteServiceMapImpl;
import com.meli.itacademy.services.ProyectoServiceMapImpl;
import com.meli.itacademy.services.UsuarioServiceMapImpl;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public abstract class ApiRouter {

    public static final UsuarioServiceMapImpl usuarioService = new UsuarioServiceMapImpl();
    public static final ProyectoServiceMapImpl proyectoService = new ProyectoServiceMapImpl();
    public static final IncidenteServiceMapImpl incidenteService = new IncidenteServiceMapImpl();


    // USUARIOS

    /**
     * Obtener todos los usuarios
     * @param request
     * @param response
     * @return String
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
     * @return String
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
     * @return String
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
     * @return String
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
     * @return String
     */
    public static String deleteUsuario(Request request, Response response) {
        response.type("application/json");

        usuarioService.deleteUsuario(Integer.parseInt(request.params(":id")));
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                "Usuario Borrado."));
    }

    // PROYECTOS

    /**
     * Obtinene todos los proyectos
     * @param request
     * @param response
     * @return String
     */
    public static String getProyectos(Request request, Response response) {
        response.type("application/json");

        if (!request.queryParams().isEmpty()) {
            if (!request.queryParams("propietario").isEmpty()) {

                Proyecto[] proyectos = proyectoService.getProyectos().stream().filter(
                        p -> p.getPropietario().getId() == Integer.parseInt(request.queryParams("propietario"))).toArray(Proyecto[]::new);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectos)));
            }
        }

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(proyectoService.getProyectos())));
    }

    /**
     * Obtiene un proyecto por id
     * @param request
     * @param response
     * @return String
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
     * @return String
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
     * @return String
     */
    public static String editProyecto(Request request, Response response) {
        response.type("application/json");

        Proyecto proyectoEditado = new Gson().fromJson(request.body(), Proyecto.class);

        proyectoEditado.setPropietario(usuarioService.getUsuario(proyectoEditado.getPropietario().getId()));
        Proyecto proyectoCambiado = proyectoService.editProyecto(proyectoEditado);

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
     * @return String
     */
    public static String deleteProyecto(Request request, Response response) {
        response.type("application/json");

        proyectoService.deleteProyecto(Integer.parseInt(request.params(":id")));
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                "Proyecto Borrado."));
    }

    // INCIDENTES

    /**
     * Obtiene todos los incidentes
     * @param request
     * @param response
     * @return String
     */
    public static String getIncidentes(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(incidenteService.getIncidentes())));
    }

    /**
     * Obtiene un incidente por id
     * @param request
     * @param response
     * @return String
     */
    public static String getIncidente(Request request, Response response) {
        response.type("application/json");

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(incidenteService.getIncidente(Integer.parseInt(request.params(":id"))))));
    }

    /**
     * Añadir un incidente
     * @param request
     * @param response
     * @return
     */
    public static String addIncidente(Request request, Response response) {
        response.type("application/json");

        Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);

        incidente.setProyecto(proyectoService.getProyecto(incidente.getProyecto().getId()));

        incidente.setReportador(usuarioService.getUsuario(incidente.getReportador().getId()));
        incidente.setResponsable(usuarioService.getUsuario(incidente.getResponsable().getId()));

        incidente.setFechaCreado(LocalDateTime.now());
        incidente.setFechaCierre(null);

        incidenteService.addIncidente(incidente);
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                "Incidente Creado."));
    }

    /**
     * Añadir descripcion a un incidente
     * @param request
     * @param response
     * @return
     */
    public static String appendDescripcionIncidente(Request request, Response response) {
        response.type("application/json");

        Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);

        incidenteService.addDescripcion(incidente.getId(), incidente.getDescripcion());

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(incidenteService.getIncidente(incidente.getId()))));
    }

    /**
     * Solucionar un incidente
     * @param request
     * @param response
     * @return
     */
    public static String solveIncidente(Request request, Response response) {
        response.type("application/json");

        Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);

        incidenteService.setSolucionado(incidente.getId());

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                new Gson().toJsonTree(incidenteService.getIncidente(incidente.getId()))));
    }



}
