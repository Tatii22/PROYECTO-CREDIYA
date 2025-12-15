package com.tati.repository.pago;

import com.tati.model.Pago;
import java.util.List;

public interface PagoRepository {
    void save(Pago pago);

    List<Pago> findByPrestamoId(int prestamoId);
}
