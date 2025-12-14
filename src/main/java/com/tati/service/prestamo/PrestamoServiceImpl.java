package com.tati.service.prestamo;

import java.time.LocalDate;
import java.util.List;

import com.tati.model.Prestamo;
import com.tati.repository.prestamo.PrestamoRepository;

public class PrestamoServiceImpl implements PrestamoService {
    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void crearPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser null");            
        }

        if (prestamo.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (prestamo.getCuotas() <= 0) {
            throw new IllegalArgumentException("Numero de cuotas invalido");
        }

        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaVencimiento(
                prestamo.getFechaInicio().plusMonths(prestamo.getCuotas())
        );

        prestamo.setSaldoPendiente(prestamo.calcularMontoTotal());

        prestamoRepository.save(prestamo);
    }
    
    @Override
    public void registrarPago(int idPrestamo, double monto) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no existe");
        }

        prestamo.aplicarPago(monto);
        prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return prestamoRepository.findById(id);
    }
    @Override
    public List<Prestamo> listarPorCliente(int idCliente) {
        return prestamoRepository.findByClienteId(idCliente);
    }

    @Override
    public List<Prestamo> listarPorEstado(String estado) {
        return prestamoRepository.findByEstado(estado);
    }
}
