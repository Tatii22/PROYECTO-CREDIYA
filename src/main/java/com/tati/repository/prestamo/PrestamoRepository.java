package com.tati.repository.prestamo;

import com.tati.model.GestorPrestamos;
import com.tati.repository.common.Repository;

import java.util.List;

public interface PrestamoRepository extends Repository<GestorPrestamos> {

    List<GestorPrestamos> findByClienteId(int id);

    List<GestorPrestamos> findByEstado(String estado);

    List<GestorPrestamos> findAll();

    void actualizarSaldoYEstado(int idPrestamo, double saldo, String estado);
}