package com.dbd.entidades;

public class LeonKennedy extends Personaje {
    public LeonKennedy(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Leon" + " " + "Kennedy", 200, 25, 40);
    }

    @Override
    public void accion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accion'");
    }
}
