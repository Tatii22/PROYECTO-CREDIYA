package com.tati.controller;

import com.tati.model.Cliente;
import com.tati.model.GestorPrestamos;
import com.tati.service.reporte.ReporteService;

import java.util.List;

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }
//examen
    public List<GestorPrestamos> filtrarPrestamosActivos() {
        return reporteService.filtrarPrestamosActivos();
    }

    public List<GestorPrestamos> filtrarPrestamosVencidos() {
        return reporteService.filtrarPrestamosVencidos();
    }
    public List<GestorPrestamos> mostrarResumen() {
        return reporteService.mostrarResumen();
    }
//

    public List<Cliente> clientesMorosos() {
        return reporteService.clientesMorosos();
    }
}
