package com.tati.service.historial;

import java.util.List;
import com.tati.model.HistorialEstadoPrestamo;

public interface HistorialEstadoPrestamoService {
    List<HistorialEstadoPrestamo> obtenerHistorial(int idPrestamo);
}
