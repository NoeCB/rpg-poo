package com.dbd.arma;

public class Hacha extends Arma {
    public Hacha(String nombre, int danio, int precision) {
        super(nombre, danio, precision);
    }

    @Override
    public void usar() {
        System.out.println("El asesino lanza el hacha para atacar al superviviente");
    }

}
