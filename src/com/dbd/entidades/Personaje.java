package com.dbd.entidades;

// Estos tres imports son importantes para que no dé error, NO cambiarlos
import com.dbd.estados.Estado;
import com.dbd.habilidades.Perk;
import java.util.ArrayList;

public abstract class Personaje {
    protected String nombrePersonaje;
    protected int vidaActual;
    protected int vidaMax;
    protected int defensaBase;
    protected int puntosSangre;
    
    // Las listas deben ser de objetos, no de Strings
    protected ArrayList<Estado> estados;
    protected ArrayList<Perk> perks;

    public Personaje(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaActual = vidaActual;
        this.vidaMax = vidaMax;
        this.defensaBase = defensaBase;
        this.puntosSangre = puntosSangre;
        this.estados = new ArrayList<>();
        this.perks = new ArrayList<>();
    }

    // Métodos Getters y Setters
    public String getNombrePersonaje() { return nombrePersonaje; }
    public int getVidaActual() { return vidaActual; }
    public void setVidaActual(int vidaActual) { this.vidaActual = vidaActual; }
    public ArrayList<Perk> getPerks() { return perks; }

    // Métodos para el combate y los turnos
    public void aplicarEstado(Estado nuevoEstado) {
        this.estados.add(nuevoEstado);
    }

    public void procesarEstados() {
        for (int i = estados.size() - 1; i >= 0; i--) {
            Estado e = estados.get(i);
            e.aplicarEfecto(this); 
            
            if (e.getTurnosRestantes() <= 0) {
                estados.remove(i); 
            }
        }
    }

    // Método abstracto obligatorio para las clases hijas
    // Cada personaje tendrá su propia implementación de acción
    public abstract void accion();
}