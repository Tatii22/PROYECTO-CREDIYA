package com.tati.controller;

import com.tati.model.Pago;
import com.tati.service.pago.PagoService;
import java.time.LocalDate;
import java.util.List;

public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public void registrarPago(int idPrestamo, double monto, LocalDate fechaPago) {
        pagoService.registrarPago(idPrestamo, monto, fechaPago);
    }

    public List<Pago> listarPagosPorPrestamo(int idPrestamo) {
        return pagoService.listarPagosPorPrestamo(idPrestamo);
    }
}
