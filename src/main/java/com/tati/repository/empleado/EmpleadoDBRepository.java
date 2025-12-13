package com.tati.repository.empleado;

import com.tati.model.Empleado;
import com.tati.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDBRepository implements EmpleadoRepository {


    @Override
    public void save(Empleado empleado) {

        String sqlUsuario = """
            INSERT INTO usuarios (nombre, documento, correo, salario, usuario, contrasena)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empleado.getNombre());
            ps.setInt(2, empleado.getDocumento());
            ps.setString(3, empleado.getCorreo());
            ps.setDouble(4, empleado.getSalario());
            ps.setString(5, empleado.getUsuario());
            ps.setString(6, empleado.getContrasena());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                int roleId = obtenerRoleId(conn, "EMPLEADO");
                asignarRol(conn, userId, roleId);
            }

        } catch (SQLException e) {
            System.out.println("Error guardando empleado: " + e.getMessage());
        }
    }


    @Override
    public List<Empleado> findAll() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = """
        SELECT u.*
        FROM usuarios u
        JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
        JOIN roles r ON ur.id_rol = r.id_rol
        WHERE r.nombre_rol = 'EMPLEADO'
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setId(rs.getInt("id_usuario"));
                e.setNombre(rs.getString("nombre"));
                e.setDocumento(rs.getInt("documento"));
                e.setCorreo(rs.getString("correo"));
                e.setSalario(rs.getDouble("salario"));
                e.setUsuario(rs.getString("usuario"));
                empleados.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error listando empleados: " + e.getMessage());
        }

        return empleados;
    }
    

    @Override public Empleado findById(int id) { return null; }

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
