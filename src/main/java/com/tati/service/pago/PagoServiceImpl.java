package com.tati.service.pago;

import com.tati.model.Pago;
import com.tati.model.Prestamo;
import com.tati.repository.pago.PagoRepository;
import com.tati.repository.prestamo.PrestamoRepository;

import java.time.LocalDate;
import java.util.List;

public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final PrestamoRepository prestamoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository,
                           PrestamoRepository prestamoRepository) {
        this.pagoRepository = pagoRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void registrarPago(int idPrestamo, double monto) {

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }

        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no existe");
        }

        
        prestamo.aplicarPago(monto);

        
        Pago pago = new Pago();
        pago.setPrestamoId(idPrestamo);
        pago.setFechaPago(LocalDate.now());
        pago.setMonto(monto);

        pagoRepository.save(pago);

        
        prestamoRepository.actualizarSaldoYEstado(
                prestamo.getId(),
                prestamo.getSaldoPendiente(),
                prestamo.getEstado().name()
        );
    }

    @Override
    public List<Pago> listarPagosPorPrestamo(int idPrestamo) {

        if (idPrestamo <= 0) {
            throw new IllegalArgumentException("ID de préstamo inválido");
        }

        return pagoRepository.findByPrestamoId(idPrestamo);
    }
}
    