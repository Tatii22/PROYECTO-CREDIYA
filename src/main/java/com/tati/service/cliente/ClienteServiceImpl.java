package com.tati.service.cliente;

import com.tati.model.Cliente;
import com.tati.repository.cliente.ClienteRepository;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(int id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
