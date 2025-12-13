package com.tati.service.usuario;


import com.tati.model.Usuario;
import com.tati.repository.usuario.UsuarioRepository;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario login(String usuario, String contrasena) {

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Usuario user = usuarioRepository.login(usuario, contrasena);

        if (user == null) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return user;
    }

    @Override
    public String obtenerRol(int idUsuario) {

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        return usuarioRepository.findRolByUsuarioId(idUsuario);
    }
}
