package com.dbd.entidades;

public class Legion extends Personaje {

    public Legion(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Legion", 180, 25, 35);

    }

    @Override
    public void accion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accion'");
    }
}
