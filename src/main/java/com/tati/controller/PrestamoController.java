package com.tati.controller;

import com.tati.model.GestorPrestamos;
import com.tati.service.prestamo.PrestamoService;

import java.util.List;

public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    public void crearPrestamo(GestorPrestamos prestamo) {
        prestamoService.crearPrestamo(prestamo);
    }

    public void registrarPago(int idPrestamo, double monto) {
        prestamoService.registrarPago(idPrestamo, monto);
    }

    public GestorPrestamos buscarPrestamo(int id) {
        return prestamoService.buscarPorId(id);
    }

    public List<GestorPrestamos> listarPrestamosPorCliente(int idCliente) {
        return prestamoService.listarPorCliente(idCliente);
    }

    public List<GestorPrestamos> listarPrestamosPorEstado(String estado) {
        return prestamoService.listarPorEstado(estado);
    }
    public List<GestorPrestamos> listarTodosPrestamos() {
        return prestamoService.listarTodosPrestamos();
    }
}
