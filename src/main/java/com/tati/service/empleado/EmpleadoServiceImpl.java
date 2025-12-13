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

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del empleado es obligatorio");
        }

        if (empleado.getSalario() <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a 0");
        }

        empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarPorId(int id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }
}
