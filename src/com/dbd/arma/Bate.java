package com.dbd.arma;

import com.dbd.core.Util;

public class Bate extends Arma {
    public Bate() {
        // Daño equilibrado (15), precisión media (75)
        super("Bate de Béisbol", 15, 75);
    }

    @Override
    public void usar() {
        System.out.println(Util.AMARILLO + "El superviviente balancea el Bate con fuerza..." + Util.RESET);
    }
}
