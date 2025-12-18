package com.tati.service.historial;

import com.tati.model.HistorialEstadoPrestamo;
import com.tati.repository.historial.HistorialEstadoPrestamoRepository;
import java.util.List;

public class HistorialEstadoPrestamoServiceImpl
        implements HistorialEstadoPrestamoService {

    private final HistorialEstadoPrestamoRepository repository;

    public HistorialEstadoPrestamoServiceImpl(
            HistorialEstadoPrestamoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HistorialEstadoPrestamo> obtenerHistorial(int idPrestamo) {
        return repository.findByPrestamoId(idPrestamo);
    }
}
