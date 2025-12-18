package com.tati.model;

import java.time.LocalDate;

import com.tati.exception.OperacionNoPermitidaException;

public class Prestamo {

    private int id;

    // Relaciones
    private Cliente cliente;
    private Empleado empleado;

    // Datos del préstamo
    private double monto;
    private double interes;
    private int cuotas;

    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;

    private double saldoPendiente;
    private EstadoPrestamo estado;

    public Prestamo() {
        this.estado = EstadoPrestamo.PENDIENTE;
        this.fechaInicio = LocalDate.now();
    }

    public Prestamo(int id, Cliente cliente, Empleado empleado,
                    double monto, double interes, int cuotas,
                    LocalDate fechaInicio, LocalDate fechaVencimiento,
                    double saldoPendiente, EstadoPrestamo estado) {

        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
    }

    public double calcularMontoTotal() {
        return monto + (monto * interes / 100);
    }

    public double calcularCuotaMensual() {
        return calcularMontoTotal() / cuotas;
    }

    public void inicializarPrestamo() {
        this.fechaInicio = LocalDate.now();
        this.fechaVencimiento = fechaInicio.plusMonths(cuotas);
        this.saldoPendiente = calcularMontoTotal();
        this.estado = EstadoPrestamo.PENDIENTE;
    }

    public void aplicarPago(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        saldoPendiente -= monto;

        if (saldoPendiente <= 0) {
            saldoPendiente = 0;
            estado = EstadoPrestamo.PAGADO;
        }
    }

    public void verificarVencimiento() {
        if (estado == EstadoPrestamo.PENDIENTE &&
                LocalDate.now().isAfter(fechaVencimiento)) {
            estado = EstadoPrestamo.VENCIDO;
        }
    }

    public int calcularCuotasPagadas() {
        double total = calcularMontoTotal();
        double pagado = total - saldoPendiente;
        return (int) Math.floor(pagado / calcularCuotaMensual());
    }
    public LocalDate getFechaCuotaActual() {
        int cuotasPagadas = calcularCuotasPagadas();
        return fechaInicio.plusMonths(cuotasPagadas);
    }
    public boolean estaVencido() {
        return LocalDate.now().isAfter(getFechaCuotaActual());
    }
    // Getters y setters

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Empleado getEmpleado() { return empleado; }
    public double getMonto() { return monto; }
    public double getInteres() { return interes; }
    public int getCuotas() { return cuotas; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public double getSaldoPendiente() { return saldoPendiente; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) {this.estado = estado;}


    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setMonto(double monto) {
        if (monto <= 0) {
            throw new OperacionNoPermitidaException("El monto debe ser mayor a cero");
        }
        this.monto = monto;
    }
    public void setInteres(double interes) {
        this.interes = interes;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + id +
                ", monto=" + monto +
                ", interes=" + interes +
                ", cuotas=" + cuotas +
                ", saldoPendiente=" + saldoPendiente +
                ", estado=" + estado + "]";
    }
}
