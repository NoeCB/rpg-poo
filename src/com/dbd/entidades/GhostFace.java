package com.dbd.entidades;

public class GhostFace extends Personaje {
    public GhostFace() {
        super("Ghost Face", 150, 150, 15, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se asoma por una esquina acechando en silencio...");
    }
}