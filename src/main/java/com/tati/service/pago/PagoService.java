package com.tati.service.pago;

import com.tati.model.Pago;
import java.util.List;
import java.time.LocalDate;

public interface PagoService {

    void registrarPago(int idPrestamo, double monto, LocalDate fechaPago);

    List<Pago> listarPagosPorPrestamo(int idPrestamo);
    
    void pagarCuotaVencida(int idPrestamo, double monto);

}
