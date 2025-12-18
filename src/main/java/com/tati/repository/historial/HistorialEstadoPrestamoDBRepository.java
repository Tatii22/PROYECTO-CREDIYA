package com.tati.repository.historial;

import com.tati.model.HistorialEstadoPrestamo;
import com.tati.utils.DatabaseConnection;
import com.tati.model.EstadoPrestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialEstadoPrestamoDBRepository
        implements HistorialEstadoPrestamoRepository {

    @Override
    public void save(HistorialEstadoPrestamo h) {

        String sql = """
            INSERT INTO historial_estado_prestamo
            (id_prestamo, estado_anterior, estado_nuevo, fecha_cambio)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, h.getIdPrestamo());
            ps.setString(2, h.getEstadoAnterior().name());
            ps.setString(3, h.getEstadoNuevo().name());
            ps.setTimestamp(4, Timestamp.valueOf(h.getFechaCambio()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error guardando historial", e);
        }
    }

    @Override
    public List<HistorialEstadoPrestamo> findByPrestamoId(int idPrestamo) {

        List<HistorialEstadoPrestamo> lista = new ArrayList<>();

        String sql = """
            SELECT estado_anterior, estado_nuevo, fecha_cambio
            FROM historial_estado_prestamo
            WHERE id_prestamo = ?
            ORDER BY fecha_cambio
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new HistorialEstadoPrestamo(
                        idPrestamo,
                        EstadoPrestamo.valueOf(rs.getString("estado_anterior")),
                        EstadoPrestamo.valueOf(rs.getString("estado_nuevo")),
                        rs.getTimestamp("fecha_cambio").toLocalDateTime()
                ));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}
