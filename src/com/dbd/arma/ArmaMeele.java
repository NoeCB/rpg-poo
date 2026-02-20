package com.dbd.arma;

public class ArmaMeele extends Arma {

    private int durabilidad;

    public ArmaMeele(String nombreArma, int dañoBase, int durabilidad) {

        super(nombreArma, dañoBase);
        this.durabilidad = durabilidad;
    }

    @Override
    public void usar() {

    }
}
