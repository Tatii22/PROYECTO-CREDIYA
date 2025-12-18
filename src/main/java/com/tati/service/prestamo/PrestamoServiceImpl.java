package com.tati.service.prestamo;

import java.util.List;

import com.tati.model.Prestamo;
import com.tati.repository.prestamo.PrestamoRepository;
import com.tati.exception.EntidadNoEncontradaException;

public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void crearPrestamo(Prestamo prestamo) {

        if (prestamo == null) {
            throw new EntidadNoEncontradaException("El préstamo no puede ser null");
        }

        if (prestamo.getMonto() <= 0) {
            throw new EntidadNoEncontradaException("El monto debe ser mayor que cero");
        }

        if (prestamo.getCuotas() <= 0) {
            throw new EntidadNoEncontradaException("Número de cuotas inválido");
        }

        if (prestamo.getCliente() == null || prestamo.getEmpleado() == null) {
            throw new EntidadNoEncontradaException("Cliente y empleado son obligatorios");
        }

      
        prestamo.inicializarPrestamo();

        prestamoRepository.save(prestamo);
    }

    @Override
    public void registrarPago(int idPrestamo, double monto) {

        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new EntidadNoEncontradaException("El préstamo no existe");
        }

        prestamo.aplicarPago(monto);

        prestamoRepository.actualizarSaldoYEstado(
                prestamo.getId(),
                prestamo.getSaldoPendiente(),
                prestamo.getEstado().name()
        );
    }

    @Override
    public Prestamo buscarPorId(int id) {
        if (id <= 0) {
            throw new EntidadNoEncontradaException("ID inválido");
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

    @Override
    public List<Prestamo> listarTodosPrestamos() {
        return prestamoRepository.findAll();
    }
}
