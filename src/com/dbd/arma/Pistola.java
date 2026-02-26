package com.dbd.arma;

public class Pistola extends Arma {
    public Pistola() {
        super("Pistola", 30, 80);
    }

    @Override
    public void usar() {
        System.out.println("El asesino dispara la pistola al superviviente");
    }

}
