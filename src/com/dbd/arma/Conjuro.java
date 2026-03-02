package com.dbd.arma;

public class Conjuro extends Arma {
    public Conjuro(String nombre, int danio, int precision) {
        super(nombre, danio, precision);
    }
    @Override
    public void usar() {
        System.out.println("Sable ha lanzado un conjuro con daño de " + this.danio);
    }
}
