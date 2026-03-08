package com.dbd.arma;

public class VinculoDeCondena extends Arma {
    public VinculoDeCondena() {
        // Nombre, Daño, Munición, Precisión
        super("Vinculo de Condena", 10, 90);
    }

    @Override
    public void usar() {
        System.out.println("Onryo ha lanzado un vinculo de condena con daño de ");
    }
}
