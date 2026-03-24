package com.dbd.arma;

import com.dbd.core.Util;

public class Motosierra extends Arma {
    public Motosierra() {
        // Daño enorme (40), poca precisión (40) por lo pesada que es
        super("Motosierra", 40, 40);
    }

    @Override
    public void usar() {
        System.out.println(Util.ROJO + "El killer enciende la Motosierra y carga con furia..." + Util.RESET);
    }
}
