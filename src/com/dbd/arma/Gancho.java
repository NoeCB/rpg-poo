package com.dbd.arma;

import com.dbd.core.Util;

public class Gancho extends Arma {
    public Gancho() {
        // Daño alto (25), precisión alta (85)
        super("Gancho Oxidado", 25, 85);
    }

    @Override
    public void usar() {
        System.out.println(Util.ROJO + "El killer lanza el Gancho con precisión letal..." + Util.RESET);
    }
}
