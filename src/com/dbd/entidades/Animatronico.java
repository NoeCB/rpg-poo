package com.dbd.entidades;

import com.dbd.arma.Arma;
import java.util.Random;

import com.dbd.arma.Hacha;

public class Animatronico extends Personaje {
    public Animatronico() {
        super("Animatrónico", 220, 220, 20, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " emite un sonido mecánico aterrador.");
    }
}
