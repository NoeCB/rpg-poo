package com.dbd.arma;

public class Pistola extends Arma {
    public Pistola(String nombre, int danio, int precision) {
        super(nombre, danio, precision);
    }

    @Override
    public void usar() {
        System.out.println("El asesino dispara la pistola al superviviente");
    }

}
