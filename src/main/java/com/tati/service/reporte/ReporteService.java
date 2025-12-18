package com.tati.service.reporte;

import com.tati.model.Cliente;
import com.tati.model.GestorPrestamos;

import java.util.List;

public interface ReporteService {

    List<GestorPrestamos> filtrarPrestamosActivos();
    List<GestorPrestamos> filtrarPrestamosVencidos();
    List<Cliente> clientesMorosos();
    List<GestorPrestamos> mostrarResumen();
}
