package com.tati.service.pago;

import com.tati.model.Pago;
import java.util.List;

public interface PagoService {

    void registrarPago(int idPrestamo, double monto);

    List<Pago> listarPagosPorPrestamo(int idPrestamo);
}
