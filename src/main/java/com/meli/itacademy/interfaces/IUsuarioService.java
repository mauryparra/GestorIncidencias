package com.meli.itacademy.interfaces;

import com.meli.itacademy.models.Usuario;

import java.util.Collection;

public interface IUsuarioService {

    Collection<Usuario> getUsuarios();
    Usuario getUsuario(int id);
    void addUsuario(Usuario usuario);
    Usuario editUsuario(Usuario usuario);
    void deleteUsuario(int id);
}
