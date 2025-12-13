package com.tati.service.cliente;

import com.tati.model.Cliente;
import java.util.List;

public interface ClienteService {

    void registrarCliente(Cliente cliente);

    Cliente buscarPorId(int id);

    List<Cliente> listarClientes();
}
