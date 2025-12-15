package com.tati.controller;

import com.tati.model.Pago;
import com.tati.service.pago.PagoService;

import java.util.List;

public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public void registrarPago(int idPrestamo, double monto) {
        pagoService.registrarPago(idPrestamo, monto);
    }

    public List<Pago> listarPagosPorPrestamo(int idPrestamo) {
        return pagoService.listarPagosPorPrestamo(idPrestamo);
    }
}
