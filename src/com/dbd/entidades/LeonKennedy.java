package com.dbd.entidades;
import com.dbd.arma.Arma;
import com.dbd.arma.Pistola;

public class LeonKennedy extends Personaje {
    private Pistola pistola;
    public LeonKennedy(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Leon" + " " + "Kennedy", 200, 25, 40);
        this.pistola = new Pistola();
    }

    @Override
    public void accion() {
        java.util.Random rd = new java.util.Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.pistola.getPrecision()) {
            System.out.println(this.nombrePersonaje + " ha atacado con su arma causando " + this.danioBase + " puntos de daño.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
}