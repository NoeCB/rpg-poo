package com.dbd.arma;

public class Conjuro extends Arma {
    public Conjuro() {
        super("conjuro", 20, 80);
    }
    @override
    public void usar() {
        System.out.println("Lanzando el conjuro " + getNombre() + " con un daño de " + getDaño());
    }
}
