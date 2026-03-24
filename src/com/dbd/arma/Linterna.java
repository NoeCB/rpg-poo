package com.dbd.arma;

import com.dbd.core.Util;

public class Linterna extends Arma {
    public Linterna() {
        // Daño muy bajo (5), mucha precisión (90)
        super("Linterna", 5, 90);
    }

    @Override
    public void usar() {
        System.out.println(Util.AMARILLO + "El superviviente enciende la Linterna apuntando a los ojos..." + Util.RESET);
    }
}
