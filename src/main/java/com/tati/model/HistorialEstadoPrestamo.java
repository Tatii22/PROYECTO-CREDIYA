package com.tati.model;

import java.time.LocalDateTime;

public class HistorialEstadoPrestamo {

    private int id;
    private int idPrestamo;
    private EstadoPrestamo estadoAnterior;
    private EstadoPrestamo estadoNuevo;
    private LocalDateTime fechaCambio;

    public HistorialEstadoPrestamo() {}

 
    public HistorialEstadoPrestamo(int idPrestamo,
                                   EstadoPrestamo estadoAnterior,
                                   EstadoPrestamo estadoNuevo) {

        this.idPrestamo = idPrestamo;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = LocalDateTime.now();
    }


    public HistorialEstadoPrestamo(int idPrestamo,
                                   EstadoPrestamo estadoAnterior,
                                   EstadoPrestamo estadoNuevo,
                                   LocalDateTime fechaCambio) {

        this.idPrestamo = idPrestamo;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = fechaCambio;
    }


    public int getId() { return id; }
    public int getIdPrestamo() { return idPrestamo; }
    public EstadoPrestamo getEstadoAnterior() { return estadoAnterior; }
    public EstadoPrestamo getEstadoNuevo() { return estadoNuevo; }
    public LocalDateTime getFechaCambio() { return fechaCambio; }


    public void setId(int id) { this.id = id; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }
    public void setEstadoAnterior(EstadoPrestamo estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }
    public void setEstadoNuevo(EstadoPrestamo estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }
    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
