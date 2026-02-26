package com.dbd.arma;

public class Hacha extends Arma {
    public Hacha() {
        super("Vinculo de Condena", 10, 80);
    }

    @Override
    public void usar() {
        System.out.println("El asesino lanza el hacha para atacar al superviviente");
    }

}
