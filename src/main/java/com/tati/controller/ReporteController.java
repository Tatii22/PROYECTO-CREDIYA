package com.tati.controller;

import com.tati.model.Cliente;
import com.tati.model.Prestamo;
import com.tati.service.reporte.ReporteService;

import java.util.List;

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public List<Prestamo> prestamosActivos() {
        return reporteService.prestamosActivos();
    }

    public List<Prestamo> prestamosVencidos() {
        return reporteService.prestamosVencidos();
    }

    public List<Cliente> clientesMorosos() {
        return reporteService.clientesMorosos();
    }
}
