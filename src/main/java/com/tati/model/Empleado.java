package com.tati.model;

public class Empleado extends Usuario {

    private double salario;

    public Empleado() {}

    public Empleado(int id, String nombre, int documento, String correo,
                    String usuario, String contrasena, double salario) {
        super(id, nombre, documento, correo, usuario, contrasena);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}