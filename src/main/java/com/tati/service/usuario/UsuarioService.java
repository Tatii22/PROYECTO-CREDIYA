package com.tati.service.usuario;

import com.tati.model.Usuario;
public interface UsuarioService {

    Usuario login(String usuario, String contrasena);

    String obtenerRol(int idUsuario);
}
