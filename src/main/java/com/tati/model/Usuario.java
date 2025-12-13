package com.tati.model;

public class Usuario {
    private int id;
    private String nombre;
    private int documento;
    private String correo;
    private String usuario;
    private String contrasena;
    
    public Usuario(int id, String nombre, int documento, String correo, String usuario, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Usuario() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", correo=" + correo
                + ", usuario=" + usuario + ", contrasena=" + contrasena + "]";
    }
    
}
