package com.tati.service.reporte;

import com.tati.model.Cliente;
import com.tati.model.EstadoPrestamo;
import com.tati.model.GestorPrestamos;
import com.tati.repository.cliente.ClienteRepository;
import com.tati.repository.prestamo.PrestamoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ReporteServiceImpl implements ReporteService {

    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;

    public ReporteServiceImpl(PrestamoRepository prestamoRepository,
                              ClienteRepository clienteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
    }
//examen
    @Override
    public List<GestorPrestamos> filtrarPrestamosActivos() {
        return prestamoRepository.findAll().stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.PENDIENTE)
                .collect(Collectors.toList());
    }

    @Override
    public List<GestorPrestamos> filtrarPrestamosVencidos() {
        return prestamoRepository.findAll().stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.VENCIDO)
                .collect(Collectors.toList());
    }
    @Override
    public List<GestorPrestamos> mostrarResumen() {
        return prestamoRepository.findAll();
    }

//
    @Override
    public List<Cliente> clientesMorosos() {
        return clienteRepository.findClientesMorosos();
    }

}
