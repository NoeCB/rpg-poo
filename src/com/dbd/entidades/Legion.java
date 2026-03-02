package com.dbd.entidades;
import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;
public class Legion extends Personaje {
    private Cuchillo cuchillo;

    public Legion(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Legion", 180, 25, 35);
        this.cuchillo = new com.dbd.arma.Cuchillo("Cuchillo de Caza", 10, 80);

    }

    @Override
    public void accion() {
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.cuchillo.getPrecision()) {
            System.out.println("Ghost Face ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
}
