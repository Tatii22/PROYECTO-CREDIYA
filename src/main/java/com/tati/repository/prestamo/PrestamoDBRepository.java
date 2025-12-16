package com.tati.repository.prestamo;


import com.tati.model.EstadoPrestamo;
import com.tati.model.Prestamo;
import com.tati.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDBRepository implements PrestamoRepository {

    @Override
    public void save(Prestamo prestamo) {

        String sql = """
            INSERT INTO prestamos
            (cliente_id, empleado_id, monto, interes, cuotas,
             fecha_inicio, fecha_vencimiento, saldo_pendiente, estado)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getCliente().getId());
            ps.setInt(2, prestamo.getEmpleado().getId());
            ps.setDouble(3, prestamo.getMonto());
            ps.setDouble(4, prestamo.getInteres());
            ps.setInt(5, prestamo.getCuotas());
            ps.setDate(6, Date.valueOf(prestamo.getFechaInicio()));
            ps.setDate(7, Date.valueOf(prestamo.getFechaVencimiento()));
            ps.setDouble(8, prestamo.getSaldoPendiente());
            ps.setString(9, prestamo.getEstado().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error guardando préstamo", e);
        }
    }

    @Override
    public Prestamo findById(int id) {

        String sql = "SELECT * FROM prestamos WHERE id_prestamo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapPrestamo(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Prestamo> findAll() {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                prestamos.add(mapPrestamo(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prestamos;
    }

    @Override
    public List<Prestamo> findByClienteId(int id) {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE cliente_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prestamos.add(mapPrestamo(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prestamos;
    }

    @Override
    public List<Prestamo> findByEstado(String estado) {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE estado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prestamos.add(mapPrestamo(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prestamos;
    }

    @Override
    public void actualizarSaldoYEstado(int idPrestamo, double saldo, String estado) {

        String sql = """
            UPDATE prestamos
            SET saldo_pendiente = ?, estado = ?
            WHERE id_prestamo = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, saldo);
            ps.setString(2, estado);
            ps.setInt(3, idPrestamo);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando préstamo", e);
        }
    }

    private Prestamo mapPrestamo(ResultSet rs) throws SQLException {

        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getInt("id_prestamo"));
        prestamo.setMonto(rs.getDouble("monto"));
        prestamo.setInteres(rs.getDouble("interes"));
        prestamo.setCuotas(rs.getInt("cuotas"));
        prestamo.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        prestamo.setFechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate());
        prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
        prestamo.setEstado(EstadoPrestamo.valueOf(rs.getString("estado")));

        return prestamo;
    }
   
}
