package com.tati.repository.cliente;

import java.util.List;

import com.tati.model.Cliente;
import com.tati.repository.common.Repository;

public interface ClienteRepository extends Repository<Cliente> {
    List<Cliente> findClientesMorosos();
}
