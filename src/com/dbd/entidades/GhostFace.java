package com.dbd.entidades;

public class GhostFace extends Personaje {
    public GhostFace(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Ghost" + " " + "Face", 150, 15, 25);
    }

    @Override
    public void accion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accion'");
    }
}