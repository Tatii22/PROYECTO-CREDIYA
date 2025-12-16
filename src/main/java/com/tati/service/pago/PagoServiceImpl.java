package com.tati.service.pago;

import com.tati.model.EstadoPrestamo;
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
    public void registrarPago(int idPrestamo, double monto, LocalDate ignored) {

        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no existe");
        }

        if (prestamo.getEstado() != EstadoPrestamo.PENDIENTE) {
            throw new IllegalStateException("El préstamo no admite pagos");
        }


        LocalDate fechaCuota = prestamo.getFechaCuotaActual();

        if (LocalDate.now().isAfter(fechaCuota)) {
            prestamo.setEstado(EstadoPrestamo.VENCIDO);

            prestamoRepository.actualizarSaldoYEstado(
                    prestamo.getId(),
                    prestamo.getSaldoPendiente(),
                    prestamo.getEstado().name()
            );

            throw new IllegalStateException(
                    "La cuota está vencida. Fecha límite: " + fechaCuota
            );
        }

        double cuotaExacta = prestamo.calcularCuotaMensual();

        if (monto != cuotaExacta) {
            throw new IllegalArgumentException(
                    "Debe pagar exactamente el valor de la cuota: " + cuotaExacta
            );
        }

        prestamo.aplicarPago(monto);

        Pago pago = new Pago(prestamo.getId(), fechaCuota, monto);
        pagoRepository.save(pago);

        prestamoRepository.actualizarSaldoYEstado(
                prestamo.getId(),
                prestamo.getSaldoPendiente(),
                prestamo.getEstado().name()
        );
    }

    @Override
    public List<Pago> listarPagosPorPrestamo(int idPrestamo) {
        return pagoRepository.findByPrestamoId(idPrestamo);
    }
}
