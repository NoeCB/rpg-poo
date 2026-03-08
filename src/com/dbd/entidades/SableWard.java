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
        System.out.println(this.nombrePersonaje + " murmura un cántico protector.");
    }
}
