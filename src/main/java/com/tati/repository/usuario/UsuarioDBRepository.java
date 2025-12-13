package com.tati.repository.usuario;

import com.tati.model.Usuario;
import com.tati.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDBRepository implements UsuarioRepository {

    @Override
    public void save(Usuario usuario) {
        String sql = """
            INSERT INTO usuarios (nombre, documento, correo, usuario, contrasena)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setInt(2, usuario.getDocumento());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getUsuario());
            ps.setString(5, usuario.getContrasena());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error guardando usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario login(String username, String contrasena) {
        String sql = """
            SELECT * FROM usuarios
            WHERE usuario = ? AND contrasena = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getInt("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getString("contrasena"));
                return u;
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String findRolByUsuarioId(int idUsuario) {
        String sql = """
            SELECT r.nombre_rol
            FROM roles r
            JOIN usuario_rol ur ON r.id_rol = ur.id_rol
            WHERE ur.id_usuario = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre_rol");
            }

        } catch (SQLException e) {
            System.out.println("Error obteniendo rol: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Usuario findByUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getInt("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getString("contrasena"));
                return u;
            }

        } catch (SQLException e) {
            System.out.println("Error buscando usuario: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getInt("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getString("contrasena"));
                return u;
            }

        } catch (SQLException e) {
            System.out.println("Error buscando usuario por id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setDocumento(rs.getInt("documento"));
                u.setCorreo(rs.getString("correo"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getString("contrasena"));
                usuarios.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error listando usuarios: " + e.getMessage());
        }

        return usuarios;
    }
}
