package com.dbd.entidades;

public class Logro {
    private int idLogro;
    private String nombre;
    private String descripcion;
    private boolean conseguido;

    public Logro(int idLogro, String nombre, String descripcion, boolean conseguido) {
        this.idLogro = idLogro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.conseguido = conseguido;
    }

    public int getIdLogro() { return idLogro; }
    public void setIdLogro(int idLogro) { this.idLogro = idLogro; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isConseguido() { return conseguido; }
    public void setConseguido(boolean conseguido) { this.conseguido = conseguido; }
}
