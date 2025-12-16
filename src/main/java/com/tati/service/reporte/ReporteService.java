package com.tati.service.reporte;

import com.tati.model.Cliente;
import com.tati.model.Prestamo;

import java.util.List;

public interface ReporteService {

    List<Prestamo> prestamosActivos();
    List<Prestamo> prestamosVencidos();
    List<Cliente> clientesMorosos();
}
