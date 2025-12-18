package com.tati.service.usuario;


import com.tati.model.Usuario;
import com.tati.repository.usuario.UsuarioRepository;
import com.tati.exception.EntidadNoEncontradaException;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario login(String usuario, String contrasena) {

        if (usuario == null || usuario.isBlank()) {
            throw new EntidadNoEncontradaException("El usuario es obligatorio");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new EntidadNoEncontradaException("La contraseña es obligatoria");
        }

        Usuario user = usuarioRepository.login(usuario, contrasena);

        if (user == null) {
            throw new EntidadNoEncontradaException("Credenciales incorrectas");
        }

        return user;
    }

    @Override
    public String obtenerRol(int idUsuario) {

        if (idUsuario <= 0) {
            throw new EntidadNoEncontradaException("ID inválido");
        }

        return usuarioRepository.findRolByUsuarioId(idUsuario);
    }
}
