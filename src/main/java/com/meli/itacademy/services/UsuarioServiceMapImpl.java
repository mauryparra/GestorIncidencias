package com.meli.itacademy.services;

import com.meli.itacademy.interfaces.IUsuarioService;
import com.meli.itacademy.models.Usuario;

import java.util.Collection;
import java.util.HashMap;

public class UsuarioServiceMapImpl implements IUsuarioService {

    private HashMap<Integer, Usuario> usuarioMapImpl;

    public UsuarioServiceMapImpl() {
        this.usuarioMapImpl = new HashMap<>();
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioMapImpl.values();
    }

    @Override
    public Usuario getUsuario(int id) {
        return usuarioMapImpl.get(id);
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuario.setId(usuarioMapImpl.size() + 1);
        usuarioMapImpl.put(usuario.getId(), usuario);
    }

    @Override
    public Usuario editUsuario(Usuario usuario) {

        Usuario usuarioEditar = usuarioMapImpl.get(usuario.getId());

        if (usuarioEditar.getNombre() != usuario.getNombre())
        {
            usuarioEditar.setNombre(usuario.getNombre());
        }

        if (usuarioEditar.getApellido() != usuario.getApellido())
        {
            usuarioEditar.setApellido(usuario.getApellido());
        }
        
        return usuarioEditar;
    }

    @Override
    public void deleteUsuario(int id) {
        usuarioMapImpl.remove(id);
    }
}
