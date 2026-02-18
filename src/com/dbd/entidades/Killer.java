package com.dbd.entidades;

public class Killer extends Personaje {
  
    //por si se neccesita para alguna habilidad o algo, aunque no se me ocurre nada en este momento.
    private double radioTerror;

    public Killer(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre, double radioTerror) {
        
        super(nombrePersonaje, vidaActual, vidaMax, defensaBase,puntosSangre);

        this.radioTerror = radioTerror;
    }

    @Override
    public void accion() {

    }

}
