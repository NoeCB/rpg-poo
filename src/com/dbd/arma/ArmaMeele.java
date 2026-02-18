package com.dbd.arma;

public class ArmaMeele extends Arma {

    private int durabilidad;

    public ArmaMeele(String nombreArma, int dañoBase, double alcance, 
                     double tiempoRecuperacion, int durabilidad) {
        
        super(nombreArma, dañoBase, alcance, tiempoRecuperacion);
        this.durabilidad = durabilidad;
    }

    @Override
    public void usar() {
    
    }
}
