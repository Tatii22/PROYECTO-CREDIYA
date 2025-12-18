package com.tati.controller;

import com.tati.model.HistorialEstadoPrestamo;
import com.tati.service.historial.HistorialEstadoPrestamoService;
import java.util.List;

public class HistorialEstadoPrestamoController {

    private final HistorialEstadoPrestamoService service;

    public HistorialEstadoPrestamoController(
            HistorialEstadoPrestamoService service) {
        this.service = service;
    }

    public List<HistorialEstadoPrestamo> verHistorial(int idPrestamo) {
        return service.obtenerHistorial(idPrestamo);
    }
}

