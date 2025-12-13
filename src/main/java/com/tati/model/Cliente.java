package com.tati.model;

public class Cliente extends Usuario {

    private String telefono;

    public Cliente() {}

    public Cliente(int id, String nombre, int documento, String correo,
                   String usuario, String contrasena, String telefono) {
        super(id, nombre, documento, correo, usuario, contrasena);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return super.toString() + ", telefono=" + telefono;
    }
}
