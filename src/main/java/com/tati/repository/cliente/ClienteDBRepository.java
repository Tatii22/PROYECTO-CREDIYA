package com.tati.repository.cliente;

import com.tati.model.Cliente;
import com.tati.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDBRepository implements ClienteRepository {

    @Override
    public void save(Cliente cliente) {

        String sqlUsuario = """
            INSERT INTO usuarios (nombre, documento, correo, telefono, usuario, contrasena)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getNombre());
            ps.setInt(2, cliente.getDocumento());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getUsuario());
            ps.setString(6, cliente.getContrasena());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                int roleId = obtenerRoleId(conn, "CLIENTE");
                asignarRol(conn, userId, roleId);
            }

        } catch (SQLException e) {
            System.out.println("Error guardando cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = """
        SELECT u.*
        FROM usuarios u
        JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
        JOIN roles r ON ur.id_rol = r.id_rol
        WHERE r.nombre_rol = 'CLIENTE'
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_usuario"));
                c.setNombre(rs.getString("nombre"));
                c.setDocumento(rs.getInt("documento"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setUsuario(rs.getString("usuario"));
                c.setContrasena(rs.getString("contrasena"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error listando clientes: " + e.getMessage());
        }

        return clientes;
    }
    @Override
    public Cliente findById(int id) { return null; }

    private int obtenerRoleId(Connection conn, String nombreRol) throws SQLException {
        String sql = "SELECT id_rol FROM roles WHERE nombre_rol = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreRol);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_rol");
            }
            throw new SQLException("Rol no encontrado: " + nombreRol);
        }
    }

    private void asignarRol(Connection conn, int userId, int roleId) throws SQLException {
        String sql = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        }
    }



    
}
