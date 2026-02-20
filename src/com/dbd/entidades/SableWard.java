package com.dbd.entidades;

public class SableWard extends Personaje {
    public SableWard(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Sable" + " " + "Ward", 250, 30, 50);
    }

    @Override
    public void accion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accion'");
    }
}
