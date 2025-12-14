package com.tati.service.prestamo;

import com.tati.model.Prestamo;
import java.util.List;

public interface PrestamoService {

    void crearPrestamo(Prestamo prestamo);

    void registrarPago(int idPrestamo, double monto);

    Prestamo buscarPorId(int id);

    List<Prestamo> listarPorCliente(int idCliente);

    List<Prestamo> listarPorEstado(String estado);
}
