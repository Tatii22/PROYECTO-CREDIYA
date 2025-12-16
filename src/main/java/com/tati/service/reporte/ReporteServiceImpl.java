package com.tati.service.reporte;

import com.tati.model.Cliente;
import com.tati.model.EstadoPrestamo;
import com.tati.model.Prestamo;
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

    @Override
    public List<Prestamo> prestamosActivos() {
        return prestamoRepository.findAll().stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.PENDIENTE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> prestamosVencidos() {
        return prestamoRepository.findAll().stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.VENCIDO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cliente> clientesMorosos() {
        return clienteRepository.findClientesMorosos();
    }

}
