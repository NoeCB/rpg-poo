package com.dbd.arma;

public class Conjuro extends Arma {
    public Conjuro() {
        super("Conjuro de Sable", 40, 70);
    }
    @Override
    public void usar() {
        System.out.println("Sable ha lanzado un conjuro con daño de " + this.danio);
    }
}
