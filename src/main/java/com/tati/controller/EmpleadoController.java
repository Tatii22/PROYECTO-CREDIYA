package com.tati.controller;

import com.tati.model.Empleado;
import com.tati.service.empleado.EmpleadoService;

import java.util.List;

public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    public void crearEmpleado(Empleado empleado) {
        empleadoService.registrarEmpleado(empleado);
    }

    public List<Empleado> listarEmpleados() {
        return empleadoService.listarEmpleados();
    }

    public Empleado buscarEmpleado(int id) {
        return empleadoService.buscarPorId(id);
    }
}
