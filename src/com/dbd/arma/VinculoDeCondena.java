package com.dbd.arma;



public class VinculoDeCondena extends Arma {
    public VinculoDeCondena(String nombre, int danio, int precision) {
        // Nombre, Daño, Munición, Precisión
        super(nombre, danio, precision);
    }

    @Override
    public void usar() {
        System.out.println("Onryo ha lanzado un vinculo de condena con daño de " + this.danio);
    }
    }
