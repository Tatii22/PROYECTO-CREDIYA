package com.tati.repository.usuario;

import com.tati.model.Usuario;
import com.tati.repository.common.Repository;

public interface UsuarioRepository extends Repository<Usuario> {

    Usuario findByUsername(String username);

    Usuario login(String username, String contrasena);

    String findRolByUsuarioId(int idUsuario);
}
