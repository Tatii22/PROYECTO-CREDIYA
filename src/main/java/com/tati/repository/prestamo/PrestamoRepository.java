package com.tati.repository.prestamo;

import com.tati.model.Prestamo;
import com.tati.repository.common.Repository;

import java.util.List;

public interface PrestamoRepository extends Repository<Prestamo> {

    List<Prestamo> findByClienteId(int id);

    List<Prestamo> findByEstado(String estado);

    void actualizarSaldoYEstado(int idPrestamo, double saldo, String estado);
}