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

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null");
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (cliente.getDocumento() <= 0) {
            throw new IllegalArgumentException("Documento inválido");
        }

        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }

        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        return clienteRepository.findById(id);
    }
}
