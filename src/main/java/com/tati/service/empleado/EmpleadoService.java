package com.tati.service.empleado;

import com.tati.model.Empleado;
import java.util.List;

public interface EmpleadoService {

    void registrarEmpleado(Empleado empleado);

    Empleado buscarPorId(int id);

    List<Empleado> listarEmpleados();
    
}
