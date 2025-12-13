package com.tati.service.empleado;

import com.tati.model.Empleado;
import com.tati.repository.empleado.EmpleadoRepository;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void registrarEmpleado(Empleado empleado) {

        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (empleado.getDocumento() <= 0) {
            throw new IllegalArgumentException("Documento inválido");
        }

        if (empleado.getSalario() <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a 0");
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
            throw new IllegalArgumentException("ID inválido");
        }

        return empleadoRepository.findById(id);
    }
}
