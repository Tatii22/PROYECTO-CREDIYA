package com.tati.service.empleado;

import com.tati.model.Empleado;
import com.tati.repository.empleado.EmpleadoRepository;
import com.tati.exception.EntidadNoEncontradaException;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void registrarEmpleado(Empleado empleado) {

        if (empleado == null) {
            throw new EntidadNoEncontradaException("El empleado no puede ser null");
        }

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new EntidadNoEncontradaException("El nombre es obligatorio");
        }

        if (empleado.getDocumento() <= 0) {
            throw new EntidadNoEncontradaException("Documento inválido");
        }

        if (empleado.getSalario() <= 0) {
            throw new EntidadNoEncontradaException("El salario debe ser mayor a 0");
        }

        empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarPorId(int id) {

        if (id <= 0) {
            throw new EntidadNoEncontradaException("ID inválido");
        }

        return empleadoRepository.findById(id);
    }
}
