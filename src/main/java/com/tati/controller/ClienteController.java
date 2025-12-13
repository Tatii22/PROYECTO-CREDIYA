package com.tati.controller;

import com.tati.model.Cliente;
import com.tati.service.cliente.ClienteService;

import java.util.List;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void crearCliente(Cliente cliente) {
        clienteService.registrarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    public Cliente buscarCliente(int id) {
        return clienteService.buscarPorId(id);
    }
}
