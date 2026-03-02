package com.dbd.entidades;
import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;
public class FengMin extends Personaje {
    public FengMin() {
        super("Feng Min", 100, 100, 10, 0);
    }

    @Override
    public void accion() {
    Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.cuchillo.getPrecision()) {
            System.out.println("Feng Min ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
}