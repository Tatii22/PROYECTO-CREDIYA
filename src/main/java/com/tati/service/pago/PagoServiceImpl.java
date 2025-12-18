package com.tati.service.pago;

import com.tati.exception.EntidadNoEncontradaException;
import com.tati.exception.OperacionNoPermitidaException;
import com.tati.exception.PagoInvalidoException;
import com.tati.model.EstadoPrestamo;
import com.tati.model.HistorialEstadoPrestamo;
import com.tati.model.Pago;
import com.tati.model.Prestamo;
import com.tati.repository.historial.HistorialEstadoPrestamoRepository;
import com.tati.repository.pago.PagoRepository;
import com.tati.repository.prestamo.PrestamoRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final PrestamoRepository prestamoRepository;
    private final HistorialEstadoPrestamoRepository historialRepository;

    public PagoServiceImpl(
            PagoRepository pagoRepository,
            PrestamoRepository prestamoRepository,
            HistorialEstadoPrestamoRepository historialRepository) {

        this.pagoRepository = pagoRepository;
        this.prestamoRepository = prestamoRepository;
        this.historialRepository = historialRepository;
    }

    // ===============================
    // REGISTRAR PAGO NORMAL
    // ===============================
    @Override
    public void registrarPago(int idPrestamo, double monto, LocalDate ignored) {

        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new EntidadNoEncontradaException("El préstamo no existe");
        }

        if (prestamo.getEstado() != EstadoPrestamo.PENDIENTE) {
            throw new OperacionNoPermitidaException("El préstamo no admite pagos");
        }

        LocalDate fechaCuota = prestamo.getFechaCuotaActual();

        // ---- CUOTA VENCIDA ----
        if (LocalDate.now().isAfter(fechaCuota)) {

            EstadoPrestamo estadoAnterior = prestamo.getEstado();
            prestamo.setEstado(EstadoPrestamo.VENCIDO);

            prestamoRepository.actualizarSaldoYEstado(
                prestamo.getId(),
                prestamo.getSaldoPendiente(),
                prestamo.getEstado().name()
            );

            historialRepository.save(
                new HistorialEstadoPrestamo(
                    prestamo.getId(),
                    estadoAnterior,
                    EstadoPrestamo.VENCIDO
                )
            );

            throw new OperacionNoPermitidaException(
                "La cuota está vencida. Fecha límite: " + fechaCuota
            );
        }

        double cuotaExacta = prestamo.calcularCuotaMensual();

        // ---- PAGO INCOMPLETO ----
        if (monto < cuotaExacta) {

            EstadoPrestamo estadoAnterior = prestamo.getEstado();
            prestamo.setEstado(EstadoPrestamo.VENCIDO);

            prestamoRepository.actualizarSaldoYEstado(
                prestamo.getId(),
                prestamo.getSaldoPendiente(),
                prestamo.getEstado().name()
            );

            historialRepository.save(
                new HistorialEstadoPrestamo(
                    prestamo.getId(),
                    estadoAnterior,
                    EstadoPrestamo.VENCIDO
                )
            );

            throw new PagoInvalidoException(
                "Pago incompleto. El préstamo ha pasado a estado VENCIDO"
            );
        }

        if (monto > cuotaExacta) {
            throw new PagoInvalidoException(
                "El monto no puede ser mayor a la cuota: " + cuotaExacta
            );
        }

        // ---- PAGO CORRECTO ----
        EstadoPrestamo estadoAnterior = prestamo.getEstado();
        prestamo.aplicarPago(monto);

        if (prestamo.getSaldoPendiente() == 0) {
            prestamo.setEstado(EstadoPrestamo.PAGADO);

            historialRepository.save(
                new HistorialEstadoPrestamo(
                    prestamo.getId(),
                    estadoAnterior,
                    EstadoPrestamo.PAGADO
                )
            );
        }

        Pago pago = new Pago(prestamo.getId(), fechaCuota, monto);
        pagoRepository.save(pago);

        prestamoRepository.actualizarSaldoYEstado(
            prestamo.getId(),
            prestamo.getSaldoPendiente(),
            prestamo.getEstado().name()
        );

        generarReciboPago(prestamo.getId(), monto, fechaCuota);
    }

 
    @Override
    public void pagarCuotaVencida(int idPrestamo, double monto) {

        Prestamo prestamo = prestamoRepository.findById(idPrestamo);

        if (prestamo == null) {
            throw new EntidadNoEncontradaException("El préstamo no existe");
        }

        if (prestamo.getEstado() != EstadoPrestamo.VENCIDO) {
            throw new OperacionNoPermitidaException("El préstamo no está vencido");
        }

        double mora = prestamo.calcularMora(0.10);
        double totalAPagar = prestamo.calcularCuotaMensual() + mora;

        if (monto < totalAPagar) {
            throw new PagoInvalidoException(
                "Pago insuficiente. Total a pagar con mora: " + totalAPagar
            );
        }

        EstadoPrestamo estadoAnterior = prestamo.getEstado();

        prestamo.aplicarMora(0.10);
        prestamo.aplicarPago(prestamo.calcularCuotaMensual());
        prestamo.setEstado(EstadoPrestamo.PENDIENTE);

        historialRepository.save(
            new HistorialEstadoPrestamo(
                prestamo.getId(),
                estadoAnterior,
                EstadoPrestamo.PENDIENTE
            )
        );

        Pago pago = new Pago(prestamo.getId(), LocalDate.now(), monto);
        pagoRepository.save(pago);

        prestamoRepository.actualizarSaldoYEstado(
            prestamo.getId(),
            prestamo.getSaldoPendiente(),
            prestamo.getEstado().name()
        );

        generarReciboPago(prestamo.getId(), monto, LocalDate.now());
    }


    @Override
    public List<Pago> listarPagosPorPrestamo(int idPrestamo) {
        return pagoRepository.findByPrestamoId(idPrestamo);
    }


    private void generarReciboPago(int prestamoId, double monto, LocalDate fecha) {

        try {
            File carpeta = new File("recibos");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File(
                carpeta,
                "recibo_prestamo_" + prestamoId + "_" + fecha + ".txt"
            );

            try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
                writer.println("========= RECIBO DE PAGO =========");
                writer.println("Préstamo ID: " + prestamoId);
                writer.println("Fecha de pago: " + fecha);
                writer.println("Monto pagado: $" + monto);
                writer.println("=================================");
            }

            System.out.println("Recibo generado en: " + archivo.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al generar recibo: " + e.getMessage());
        }
    }
}
