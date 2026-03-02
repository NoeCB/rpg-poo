package com.dbd.entidades;

public class Killer extends Personaje {
  
    protected double radioTerror;

    public Killer(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre, double radioTerror) {
        // Llamamos al constructor de Personaje
        super(nombrePersonaje, vidaActual, vidaMax, defensaBase, puntosSangre);
        this.radioTerror = radioTerror;
        
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " acecha a su presa con un radio de terror de " + radioTerror + " metros.");
    }
}