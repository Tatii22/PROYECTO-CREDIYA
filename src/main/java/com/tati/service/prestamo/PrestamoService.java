package com.tati.service.prestamo;

import com.tati.model.GestorPrestamos;
import java.util.List;

public interface PrestamoService {

    void crearPrestamo(GestorPrestamos prestamo);

    void registrarPago(int idPrestamo, double monto);

    GestorPrestamos buscarPorId(int id);

    List<GestorPrestamos> listarPorCliente(int idCliente);

    List<GestorPrestamos> listarPorEstado(String estado);

    List<GestorPrestamos> listarTodosPrestamos();

}
