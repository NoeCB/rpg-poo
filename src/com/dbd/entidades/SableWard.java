package com.dbd.entidades;

import java.util.Random;
import com.dbd.arma.Conjuro;
import com.dbd.arma.Arma;

public class SableWard extends Personaje {
    public SableWard() {
        // Nombre, vidaActual, vidaMax, defensaBase, puntosSangre
        super("Sable Ward", 110, 110, 30, 0);
    }

    @Override
    public void accion() {
        Random rd = new Random();
        int impacto = rd.nextInt(1, 101);
        if (impacto <= this.conjuro.getPrecision()) {
            System.out.println("Sable Ward ha atacado con su conjuro.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
}
