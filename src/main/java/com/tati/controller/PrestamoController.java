package com.tati.controller;

import com.tati.model.Prestamo;
import com.tati.service.prestamo.PrestamoService;

import java.util.List;

public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    public void crearPrestamo(Prestamo prestamo) {
        prestamoService.crearPrestamo(prestamo);
    }

    public void registrarPago(int idPrestamo, double monto) {
        prestamoService.registrarPago(idPrestamo, monto);
    }

    public Prestamo buscarPrestamo(int id) {
        return prestamoService.buscarPorId(id);
    }

    public List<Prestamo> listarPrestamosPorCliente(int idCliente) {
        return prestamoService.listarPorCliente(idCliente);
    }

    public List<Prestamo> listarPrestamosPorEstado(String estado) {
        return prestamoService.listarPorEstado(estado);
    }
}
