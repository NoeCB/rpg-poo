package com.dbd.core;

/**
 * Representa un logro desbloqueable dentro del juego.
 * Mantiene la información del nombre, descripción y si ha sido conseguido por el jugador.
 * 
 * @author Luis Lázaro y Noelia Cantador
 * @version 1.0
 */
public class Logro {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean conseguido;

    /**
     * Constructor por defecto.
     */
    public Logro(int id, String nombre, String descripcion, boolean conseguido) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.conseguido = conseguido;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isConseguido() {
        return conseguido;
    }

    public void setConseguido(boolean conseguido) {
        this.conseguido = conseguido;
    }
}
