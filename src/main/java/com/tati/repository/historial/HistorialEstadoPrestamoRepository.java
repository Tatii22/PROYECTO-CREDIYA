package com.tati.repository.historial;

import com.tati.model.HistorialEstadoPrestamo;
import java.util.List;

public interface HistorialEstadoPrestamoRepository {

    void save(HistorialEstadoPrestamo historial);

    List<HistorialEstadoPrestamo> findByPrestamoId(int idPrestamo);
}
