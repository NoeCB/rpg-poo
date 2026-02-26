package com.dbd.arma;

public class Cuchillo extends Arma {
    public Cuchillo() {
        super("Cuchillo", 10, 80);
    }

    @Override
    public void usar() {
        System.out.println("El asesino usa el cuchillo para atacar al superviviente");
    }

}
