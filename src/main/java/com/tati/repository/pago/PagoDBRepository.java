package com.tati.repository.pago;

import com.tati.model.Pago;
import com.tati.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagoDBRepository implements PagoRepository{
    @Override
    public void save(Pago pago) {
        String sql = "INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pago.getPrestamoId());
            ps.setDate(2, Date.valueOf(pago.getFechaPago()));
            ps.setDouble(3, pago.getMonto());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error guardando pago: " + e.getMessage());
        }
    }
    
    @Override
    public List<Pago> findByPrestamoId(int prestamoId) {
        List<Pago> pagos = new ArrayList<>();
        String sql = """
            SELECT * FROM pagos
            WHERE prestamo_id = ?
            ORDER BY fecha_pago ASC
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prestamoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago();
                pago.setId(rs.getInt("id_pago"));
                pago.setPrestamoId(rs.getInt("prestamo_id"));
                pago.setFechaPago(rs.getDate("fecha_pago").toLocalDate());
                pago.setMonto(rs.getDouble("monto"));
                pagos.add(pago);
            }

        } catch (SQLException e) {
            System.out.println("Error buscando pagos por prestamo id: " + e.getMessage());
        }
        return pagos;
    }
    
}
