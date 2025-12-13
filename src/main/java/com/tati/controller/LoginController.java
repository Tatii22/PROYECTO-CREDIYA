package com.tati.controller;

import com.tati.model.Usuario;
import com.tati.service.usuario.UsuarioService;

public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario login(String usuario, String contrasena) {
        return usuarioService.login(usuario, contrasena);
    }

    public String obtenerRol(int idUsuario) {
        return usuarioService.obtenerRol(idUsuario);
    }
}
