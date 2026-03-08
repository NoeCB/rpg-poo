package com.dbd.entidades;

import com.dbd.arma.VinculoDeCondena;

import java.util.Random;
import com.dbd.arma.Arma;

public class Onryo extends Personaje {
    public Onryo() {
        super("Onryo", 220, 220, 20, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " sale de un televisor cercano.");
    }
}