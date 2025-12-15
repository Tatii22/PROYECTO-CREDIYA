package com.tati.model;

import java.time.LocalDate;

public class Prestamo {

    private int id;

    // Relaciones (FK en BD)
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

    public void verificarVencimiento() {
        if (estado == EstadoPrestamo.PENDIENTE &&
                LocalDate.now().isAfter(fechaVencimiento)) {
            estado = EstadoPrestamo.VENCIDO;
        }
    }

    public void aplicarPago(double monto) {

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        if (monto > saldoPendiente) {
            throw new IllegalArgumentException("El monto excede el saldo pendiente");
        }

        saldoPendiente -= monto;

        if (saldoPendiente == 0) {
            estado = EstadoPrestamo.PAGADO;
        } else {
            estado = EstadoPrestamo.PENDIENTE;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
        this.saldoPendiente = calcularMontoTotal(); 
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

        public void inicializarPrestamo() {
        this.fechaInicio = LocalDate.now();
        this.fechaVencimiento = fechaInicio.plusMonths(cuotas);
        this.saldoPendiente = calcularMontoTotal();
        this.estado = EstadoPrestamo.PENDIENTE;
    }


        @Override
        public String toString() {
            return "Prestamo [id=" + id + ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
           ", empleadoId=" + (empleado != null ? empleado.getId() : "null") + ", monto=" + monto
                    + ", interes=" + interes + ", cuotas=" + cuotas + ", fechaInicio=" + fechaInicio
                    + ", fechaVencimiento=" + fechaVencimiento + ", saldoPendiente=" + saldoPendiente + ", estado="
                    + estado + "]";
        }
}

